# Redis 맛보기

### Redis 설치해서 실행하기

로컬에 레디스 설치하기

> brew install redis-cli (MAC)

> apt-get install redis-server (UBUNTU)

https://hanee24.github.io/2018/01/05/how-to-install-redis-on-ubuntu-16-04/  



redis-cli로 서버에 접속하기  

> redis-cli -h [IP] -p [PORT]



protected 모드를 해제하며 레디스 서버 올리기 (외부에서 접근 가능)  

> /src/redis-server --protected-mode no



### Redis의 자료구조와 커맨드

모든 커맨드 보기 : https://redis.io/commands  

설치 없이 실행해보기 : http://try.redis.io/  



##### DATASTRUCTURE : STRING

스트링

> SET : 키에 값 세팅

> DEL : 키와 값 삭제

> EXISTS : 키가 존재하는지 확인

> INCR : 숫자 형태로 된 값 증가

> DECR : 숫자 형태로 된 값 감소

> EXPIRE : 시간이 지나면 사라지도록 TTL 설정



##### DATASTRUCTURE : LIST

순서있는 밸류의 집합  

레디스에서는 키 값이 없어도 명령을 실행하면 해당하는 키를 생성하고, 키를 생성했어도 값이 존재하지 않으면 해당 키는 사라진다  

> RPUSH : 리스트의 마지막에 추가한다 (여러개 동시에 실행 가능)

> LPUSH : 리스트의 시작에 추가한다

> LLEN : 리스트의 길이 반환

> LRANGE [key] [start] [stop] : 부분집합을 리스트로 반환한다

> LPOP : 가장 첫번째 값을 삭제하면서 반환한다

> RPOP : 리스트의 마지막 값을 삭제하면서 반환한다



##### DATASTRUCTURE : SET

순서는 없고 중복된 값을 가질 수 없는 자료구조  

> SADD : 셋에 추가 (이미 값이 있으면 0을, 값이 없으면 1을 반환한다)

> SREM : 셋에 있는 엘리먼트 삭제 (삭제했으면 1을, 삭제 안했으면 0을 반환한다)

> SISMEMBER : 셋에 값이 포함되어 있는지 반환

> SMEMBERS : 셋에 있는 모든 값 출력

> SUNION : 하나 이상의 셋을 하나로 합친 셋으로 변환하여 반환

> SPOP, SRANDMEMBER : 랜덤으로 값을 제거하고 반환



##### DATASTRUCTURE : SORTED SET

셋과 비슷한데 각 값이 스코어를 가지고 있어서 정렬 가능한 셋  

> ZADD [key] [score] [value] : 셋에 데이터 추가

> ZRANGE [key] [start] [stop] : 스코어를 바탕으로 정렬된 셋의 부분집합



##### DATASTRUCTURE : HASHES

필드를 여러개 가질 수 있는 스트링 맵  

> HSET [key] [field name] [field value] : 필드 세팅

> HGETALL : 키가 가지고 있는 모든 필드와 필드 값를 반환

> HGET [key] [field] : 키의 특정 필드 값을 반환
