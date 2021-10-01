# license-machine
- 라이센스 파일을 발급하는 모듈
# license-app
- 라이센스 파일을 검증하고 실제 고객사 코드에 import 되어야 하는 모듈

# 라이센스 신규 등록 방법
## STEP1
- 고객사로 부터 서버호스트정보 , MAC ADDR , 유효기간( 고객사가 아닌 계약정보에서 확인한다 )
## STEP2
- 최신 license-app 를 빌드하여 고객사 소스에 import
- lib folder 생성 후 해당 폴더에 복사
- build.gradle dependency 추가 implementation name: 'license-app-1.0'
- 
## STEP3
- license-machine -> LicenseMachineTest -> testIssueLicenseFile() 를 이용해 신규 라이센트 파일을 발급 한다.
- 발급된 파일을 고객사 소스 또는 서버에 복사
- license-machine > key-file > license-privkey.der 파일을 고객사 소스 또는 서버에 복사

## STEP4
- 라이센스 검증 로직 개발
