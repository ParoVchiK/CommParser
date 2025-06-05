from auth import s, get_token
from export import get_user
from time import sleep
from enum import Enum

token = get_token()


class CommentableTypes(str, Enum):
    BEATMAPSET = 'beatmapset'
    CHANGELOG = 'build'
    NEWS_POST = 'news_post'


class SortTypes(str, Enum):
    VOTES = 'votes'
    COMMS_COUNT = 'count'


def send_comment_to_db(map_comment):
    try:
        user_id = map_comment["user_id"]
        comment_id = map_comment["id"]
        player_reponse = s.get(f"http://localhost:8080/players/getPlayerById/{user_id}").json()
        comm_response = s.get(f"http://localhost:8080/comms/getComm/{comment_id}")
        comm_status = comm_response.status_code
        if map_comment["user_id"] and comm_status == 404 and not map_comment['deleted_at']:
            votes_up = map_comment["votes_count"]
            if not player_reponse:
                player = {
                    "id": user_id,
                    "votes_up": votes_up,
                    "comms_count": 1
                }
                s.post("http://localhost:8080/players/postPlayer", json=player)
                print(f"player {user_id} created")
            else:
                player = {
                    "id": user_id,
                    "votes_up": player_reponse["votes_up"] + votes_up,
                    "comms_count": player_reponse["comms_count"] + 1
                }
                s.patch("http://localhost:8080/players/updatePlayer", json=player)
                print(f"player {user_id} updated")
            data = {
                "id": comment_id,
                "user_id": user_id,
                "parent_id": map_comment["parent_id"],
                "commentable_type": map_comment["commentable_type"],
                "commentable_id": map_comment["commentable_id"],
                "msg_text": map_comment["message"],
                "msg_date": map_comment['created_at'],
                "votes": votes_up
            }
            s.post("http://localhost:8080/comms/postComms", json=data).json()
            print(f"comment {comment_id} added")
        elif map_comment["user_id"] and comm_status != 404 and not map_comment['deleted_at']:
            votes_up = map_comment["votes_count"]
            comm_data = comm_response.json()
            if votes_up != comm_data["votes"]:
                player = {
                    "id": user_id,
                    "votes_up": player_reponse["votes_up"] + votes_up - comm_data["votes"],
                    "comms_count": player_reponse["comms_count"] + 1
                }
                s.patch("http://localhost:8080/players/updatePlayer", json=player)
                data = {
                    "id": comment_id,
                    "user_id": user_id,
                    "parent_id": map_comment["parent_id"],
                    "commentable_type": map_comment["commentable_type"],
                    "commentable_id": map_comment["commentable_id"],
                    "msg_text": map_comment["message"],
                    "msg_date": map_comment['created_at'],
                    "votes": votes_up
                }
                s.patch("http://localhost:8080/comms/updateComm", json=data).json()
                print(f"comment {comment_id} updated")
                print(f"player {user_id} updated")
            else:
                print(f"no need to update comment {comment_id}")
        else:
            print(f"comment {comment_id} deleted")
    except:
        print("failed to parse json")

def recalculateUser(user_id, comms_count):
    global token
    page=1
    header = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/json",
        "Content-Type": "application/json",
    }
    response = s.get(f"https://osu.ppy.sh/api/v2/comments?user_id={user_id}&page={page}", headers=header).json()
    if not 'error' in response:
        votes_count = 0
        page = 0
        pages = (comms_count//50) + 1
        while page<pages:
            comms_data = response["comments"]
            for comm in comms_data:
                votes_count += comm["votes_count"]
                send_comment_to_db(comm)
            page+=1
            print(page)
            response = s.get(f"https://osu.ppy.sh/api/v2/comments?user_id={user_id}&page={page}", headers=header).json()
        data = {
            "id": user_id,
            "votes_up": votes_count,
            "comms_count": comms_count
        }
        print(data)
        s.patch(f"http://localhost:8080/players/updatePlayer", json=data)


def recalculatePlayersBy(votes=True):
    start_id = 0
    while start_id <= 500:
        try:
            if votes:
                repsonse = s.get("http://localhost:8080/players/getPlayersByVotesAlt").json()
            else:
                repsonse = s.get("http://localhost:8080/players/getPlayersByCountAlt").json()
            repsonse = repsonse[start_id:]
            for i in repsonse:
                user_data = get_user(i["user_id"])
                if not 'error' in user_data:
                    recalculateUser(i["user_id"], user_data["comments_count"])
            start_id+=1
        except:
            print("failed to parse json")


def recalculateCommsByCommentableType(commentable_type, commentable_id):
    header = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/json",
        "Content-Type": "application/json",
    }
    page = 1
    response = s.get(f"https://osu.ppy.sh/api/v2/comments?commentable_type={commentable_type}&commentable_id={commentable_id}&page=1", headers=header).json()
    while response['comments']:
        comms_data = response['comments']
        for comm in comms_data:
            send_comment_to_db(comm)
        page+=1
        response = s.get(
            f"https://osu.ppy.sh/api/v2/comments?commentable_type={commentable_type}&commentable_id={commentable_id}&page={page}",
            headers=header).json()
        sleep(1)


def recalculateCommentableTypesBy(commentable_type, sort_by):
    response = s.get(f"http://localhost:8080/comms/getGroupedCommentableTypesBy?commentable_type={commentable_type}&sort_by={sort_by}").json()
    print(response)
    for commentable_obj in response:
        recalculateCommsByCommentableType(commentable_type, commentable_obj["commentable_id"])
        print(f"recalculated {commentable_obj["commentable_id"]}")


#recalculateCommentableTypesBy('beatmapset', 'votes')
#recalculateCommentableTypesBy('beatmapset', 'count')
recalculateCommentableTypesBy('news_post', 'votes')
recalculateCommentableTypesBy('news_post', 'count')
recalculateCommentableTypesBy('build', 'votes')
recalculateCommentableTypesBy('build', 'count')