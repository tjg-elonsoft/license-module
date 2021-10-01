# !!!키파일 삭제 금지!!!
# app 로 복사하는 key 는 PRIVATE KEY 만 복사



# SSO용 키 및 인증서 생성하기
### https://support.google.com/cloudidentity/answer/6342198?hl=ko
Google Workspace에서는 Google Workspace 또는 Google Workspace for Education을 사용하는 고객에게 싱글 사인온(SSO) 서비스를 제공합니다. Google Workspace 싱글 사인온(SSO) 서비스는   RSA 또는 DSA 알고리즘 방식으로 생성된 공개 키와 인증서를 사용합니다. 서비스를 사용하려면 공개 및 비공개 키의 조합과 공개 키가 포함된 X.509 인증서를 생성해야 합니다. 공개 키 또는 인증서가 생성되면 이를 Google에 등록해야 합니다. Google 관리 콘솔에서 키 또는 인증서를 간단히 업로드하여 등록할 수 있습니다.

키와 인증서를 생성하는 방법은 개발 플랫폼 및 프로그래밍 언어의 환경설정에 따라 다릅니다. Google Workspace SSO 서비스에 필요한 키와 인증서는 다음과 같은 다양한 방법으로 생성할 수 있습니다.

## OpenSSL 사용
공개 키와 비공개 키 쌍을 만드는 방법은 많지만 오픈소스 OpenSSL 도구는 가장 인기 있는 도구 중 하나입니다. 모든 주요 플랫폼으로 이전되며 키 생성을 위한 간단한 명령줄 인터페이스를 제공합니다.

# RSA 비공개 키 만들기
OpenSSL을 사용해서 RSA 비공개 키를 생성하려면 다음 한 단계만 수행하면 됩니다.

```openssl genrsa -out rsaprivkey.pem 2048```

이 명령어는 PEM으로 인코딩된 비공개 키를 생성하여 rsaprivkey.pem 파일에 저장합니다. 이 예에서는 거의 모든 용도로 사용할 수 있는 2048비트 키를 만듭니다. 여기서 생성되는 비공개 키는 비밀로 유지되어야 하며 데이터 서명 및 해독에 사용됩니다.

일부 구현, 특히 Java 기반의 구현의 경우, DER 또는 PKCS8이 필요할 수 있습니다. 예를 들면 다음 추가 단계를 사용하여 생성할 수 있습니다.

```
1. openssl rsa -in rsaprivkey.pem -pubout -outform DER -out rsapubkey.der
2. openssl pkcs8 -topk8 -inform PEM -outform DER -in rsaprivkey.pem -out rsaprivkey.der -nocrypt
```
1단계에서는 DER 형식으로 공개 키를 생성합니다.
