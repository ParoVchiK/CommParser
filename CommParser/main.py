from auth import get_token, s

token = get_token()

def get_comments(id):
    global token
    uri = f"https://osu.ppy.sh/api/v2/comments/{id}"
    header = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/json",
        "Content-Type": "application/json",
    }
    try:
        return s.get(uri, headers=header).json()
    except:
        return None


start_id = 3574151


def send_comment_to_db(map_comment):
    user_id = map_comment["user_id"]
    comment_id = map_comment["id"]
    player_reponse = s.get(f"http://localhost:8080/players/getPlayerById/{user_id}").json()
    comm_response = s.get(f"http://localhost:8080/comms/getComm/{comment_id}").status_code
    if map_comment["user_id"] and comm_response == 404 and not map_comment['deleted_at']:
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
    else:
        print(f"comment {comment_id} already created or deleted")


while True:
    comments_data = get_comments(start_id)
    if comments_data and 'comments' in comments_data:
        com_data = comments_data['comments'][0]
        send_comment_to_db(com_data)
        if 'included_comments' in comments_data:
            other_comments = comments_data['included_comments']
            for com in other_comments:
                send_comment_to_db(com)
    else:
        print(comments_data)
    start_id += 1


