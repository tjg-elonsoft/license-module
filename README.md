# license-machine
- 라이센스 파일을 발급하는 모듈
# license-app
- 라이센스 파일을 검증하고 실제 고객사 코드에 import 되어야 하는 모듈

# 세팅

1. intellij community 다운로드 및 설치
2. gitlab -> elonsoft 계정으로 로그인
3. go to license-module project
4. intellij 실행
5. Get from VCS
6. 클론 path붙여넣기
7. clone
8. project 들어가기

# 라이센스 발급
## STEP1
- 고객사로 부터 호스트이름 , MAC ADDR , 유효기간( 고객사가 아닌 계약정보에서 확인한다 )(라이센스 시작일로부터 14개월간)
1. ssh(putty) 고객사 서버 접속하기
2. ifconfig
3. 계정명@호스트이름
4. ether MAC ADDR
5. elonsoft.daouoffice -> Works -> 가상자산거래소솔루션이용진행현황 -> 라이센스 시작일
## STEP2
- 최신 license-app 를 빌드하여 고객사 소스(api, was)에 import
  우측상단에 'gradle' -> module -> license-app -> Tasks -> build -> build 더블클릭
- lib folder 생성 후 해당 폴더에 복사
  폴더경로 : 워크스페이스경로\license-module\module\license-app\build\libs
- build.gradle dependency 추가 implementation name: 'license-app-1.0'

## STEP3
- license-machine -> LicenseMachineTest -> testIssueLicenseFile() 를 이용해 신규 라이센트 파일을 발급 한다.
- 발급된 파일을 고객사 소스 또는 서버에 복사
- license-machine > key-file > license-privkey.der 파일을 고객사 소스 또는 서버에 복사

## STEP4
- 라이센스 검증 로직 개발

#etc
- gitlab SSH 등록방법
1. cmd 실행
2. ssh-keygen -t rsa -b 2048 -C "<comment>"
3. 입력값은 모두 엔터로 넘김
4. C:\Users\유저명\.ssh 이동
5. id_rsa.pub 파일을 메모장으로 실행
6. 메모장내용 복사
7. https://gitlab.com/-/profile/keys
8. key에 붙여넣기, 만료일자 설정
9. Add key

# 에러발생시
1. 
```
FAILURE: Build failed with an exception.
* What went wrong:
Execution failed for task ':test'.
```
Settings > Build, Excecution, Deployment > Build Tools > Gradle 

Run tests using - [IntelliJ IDEA] 로 변경
