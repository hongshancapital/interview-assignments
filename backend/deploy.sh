docker stop app_test_client && docker rm app_test_client && docker rmi short_link-app_test_client
docker stop app_test_server && docker rm app_test_server && docker rmi short_link-app_test_server

docker stop db && docker rm db
docker stop redis && docker rm redis

docker-compose up -d