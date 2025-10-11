from fastapi import FastAPI, Request, Response
import logging
import os
import httpx

def str_to_bool(s: str) -> bool:
    return s.lower() in 'true'

def should_route_to_microservice(req_counter, migration_percent):
    req_num = round(100 / migration_percent)
    return (req_counter % req_num) == 0

def filter_headers(headers):
    restricted_headers = ("content-length", "transfer-encoding")
    res = {}
    for key, value in headers.items():
        if not key.lower() in restricted_headers:
            res[key] = value
    return res


logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

is_gradual_migration = str_to_bool(os.getenv("GRADUAL_MIGRATION", "true"))
movies_migr_percent = int(os.getenv("MOVIES_MIGRATION_PERCENT", "50"))
monolith_url = os.getenv("MONOLITH_URL", "http://localhost:8080")
movies_service_url = os.getenv("MOVIES_SERVICE_URL", "http:/localhost:8081")

app = FastAPI()

app.state.movies_request_counter = 0

@app.get("/api/proxy/health")
async def health():
    return {"status": "ok"}

@app.api_route("/{full_path:path}", methods=["GET", "POST", "PUT", "DELETE", "PATCH"])
async def router(request: Request, full_path: str):
    target_url = f"{monolith_url}/{full_path}"

    if is_gradual_migration and full_path.endswith("api/movies"):
        app.state.movies_request_counter += 1
        if should_route_to_microservice(app.state.movies_request_counter, movies_migr_percent):
            target_url = f"{movies_service_url}/{full_path}"

    body = await request.body()
    headers = dict(request.headers)
    method = request.method



    async with httpx.AsyncClient() as client:
        resp = await client.request(
            method=method,
            url=target_url,
            content=body,
            headers=headers,
            params=request.query_params
        )

    resp_headers = filter_headers(dict(resp.headers))

    return Response(
        content=resp.content,
        status_code=resp.status_code,
        headers=resp_headers,
        media_type=resp.headers.get("content-type")
    )



