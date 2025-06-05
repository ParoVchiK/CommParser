import requests
import json

s = requests.Session()
url = "https://osu.ppy.sh/oauth/token"
headers = {
    "Accept": "application/json",
    "Content-Type": "application/x-www-form-urlencoded",
}

body = json.load('conf.json')

token = s.post(url, headers=headers, data=body).json()['access_token']


def get_token():
    return token
