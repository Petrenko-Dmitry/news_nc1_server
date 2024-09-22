docker run --name test-mysql -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=news_db -e MYSQL_USER=user -e MYSQL_PASSWORD=password -p 3306:3306 -d mysql:latest
