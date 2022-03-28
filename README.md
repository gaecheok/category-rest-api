# 빌드
+ mvn package

# 실행
+ java -jar category-rest-api-0.0.1-SNAPSHOT.jar
+ 서비스 포트 : 8080
+ jdk 1.8

# 테스트
```
전체조회
curl +X GET http://localhost:8080/category

단건조회
curl +X GET http://localhost:8080/category/100

등록
curl -X POST http://localhost:8080/category -H 'Content-Type:application/json' -d '{"categoryId":400,"description":"D","referenceId":0}'

수정
curl -X PUT http://localhost:8080/category/100 -H 'Content-Type: application/json' -d '{"categoryId":100,"description":"updatedText","referenceId":0}'

삭제
curl -X DELETE http://localhost:8080/category/100
```

--------------
# data.sql 테스트 데이터
```sql
INSERT INTO Category (category_id, description, reference_id) VALUES (100, 'A', 0);
INSERT INTO Category (category_id, description, reference_id) VALUES (110, 'A-1', 100);
INSERT INTO Category (category_id, description, reference_id) VALUES (111, 'A-11', 110);
INSERT INTO Category (category_id, description, reference_id) VALUES (112, 'A-12', 110);
INSERT INTO Category (category_id, description, reference_id) VALUES (120, 'A-2', 100);
INSERT INTO Category (category_id, description, reference_id) VALUES (130, 'A-3', 100);
INSERT INTO Category (category_id, description, reference_id) VALUES (200, 'B', 0);
INSERT INTO Category (category_id, description, reference_id) VALUES (210, 'B-1', 200);
INSERT INTO Category (category_id, description, reference_id) VALUES (300, 'C', 0);
INSERT INTO Category (category_id, description, reference_id) VALUES (310, 'C-1', 300);
INSERT INTO Category (category_id, description, reference_id) VALUES (320, 'C-2', 300);
```