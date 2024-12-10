# 👋 ABOUT Doc'Tech

<div align="center">  
  
  ![로고](https://github.com/user-attachments/assets/d4a9290a-5879-4797-8f0b-a6db2c98819c)
  
<img width="417" alt="image" src="https://github.com/user-attachments/assets/85b3d706-b2bd-446b-badc-9c99ca76f584">

기존 비대면 진료에서는 과도한 개인 진료 서류 제출, 신속한 처방의 어려움, 불필요한 개인정보 노출 등의 문제가 있었습니다. <br>
이를 해결하기 위해 의료 마이데이터를 활용하여 환자가 자신의 건강 상태를 효율적으로 관리하고, <br>이를 바탕으로 비대면 진료 서비스에서 정확한 진단을 받을 수 있도록 지원합니다.
</div>
<br>


## 🎬 시연 영상

<div style="display: flex; justify-content: space-between; gap: 20px;">
  <!-- 환자 시연 영상 -->
  <div style="flex: 1; max-width: 45%;">
    <a href="https://github.com/user-attachments/assets/cb26223d-2dfe-4278-9bd5-c391fe0a3843" target="_blank" style="text-decoration: none; color: inherit;">
      <img src="https://github.com/user-attachments/assets/33f2136b-3ca0-4046-b246-c96438ca430e" style="width: 50%; height: auto;"/>
      <p style="text-align: center;">환자 시연 영상 보기</p>
    </a>
  </div>

  <!-- 의사 시연 영상 -->
  <div style="flex: 1; max-width: 45%;">
    <a href="https://github.com/user-attachments/assets/7925b290-da76-494f-97a3-a65a3decab5f" target="_blank" style="text-decoration: none; color: inherit;">
      <img src="https://github.com/user-attachments/assets/5fe3ff3c-2c5e-4823-96be-a9bda5dfd4ae" style="width: 50%; height: auto;"/>
      <p style="text-align: center;">의사 시연 영상 보기</p>
    </a>
  </div>
</div>



<br>

## 🔍 Main Features
### 1️⃣ 의료 마이데이터 연동
마이데이터 API 서버를 통해 내 의료 마이데이터(건강검진/예방접종/진료이력/투약이력)를 한 곳에서 모아볼 수 있습니다.

### 2️⃣ 비대면 진료
언제 어디서나 쉽고 간편하게 예약하고 비대면 진료를 받을 수 있습니다.<br>
이때, 비식별화된 마이데이터가 사용자 동의 하에 의사와 공유되어 보다 정확한 진료가 이루어집니다.

### 3️⃣ 빠른 지도 검색
인근 병원 · 약국 · 응급실을 지도를 통해 빠르게 찾아볼 수 있습니다.

<br>

## 👥 Developers
<div align="center">
  
| [곽수찬](https://github.com/soooochan) | [유혜선](https://github.com/Hyeseon00) |
|--------|------- |
| <img width="300px" src= "https://github.com/user-attachments/assets/beac5c0a-c58c-4374-9a2c-35e29f3f5552"> | <img width="290px" src="https://github.com/user-attachments/assets/321710ea-c916-4ea6-9948-86d309e4e8f9"> | 
| ✔️ 온보딩 화면 <br> ✔️ 회원가입/로그인 화면 <br> ✔️ 진료 내역 화면 <br> | ✔️ 환자 정보 비식별화 기능 <br> ✔️ 홈 화면 <br> ✔️ 처방전 작성 기능 <br> ✔️ 환자 의료기록 열람 및 조회 기능 <br> |
</div>

<br>

## 🎨 User Flow
<div align="center"> 
  
  ![doctech-userflow](https://github.com/user-attachments/assets/f3bb8d4c-1481-42be-aeb7-333db236df46)
</div>

<br>

## 🚀 로컬 실행 커맨드
1. node_modules 설치<br>

```
npm install --force
```
호환되지 않는 라이브러리가 있기 때문에 --force 명령어로 설치하여야 합니다.

2. 개발 환경에서 시작
```
npm start
```

<br>

## ⚒️ Stacks

| Skill | Name |
|--------|------- |
| Language | `JavaScript` |
| Framework | `React.js` |
| Library | `React Native`, `Axios`, `Expo`, `React Navigation` |

<br>

## 📜 Code Covention
| 항목                | 규칙                                                                                  |
|---------------------|---------------------------------------------------------------------------------------|
| `Function`              | **PascalCase**                                                                         |
| `Variable`                | **camelCase**                                                                    |                                                                       

<br>

## 🗂️ Package
```
├── 📁 app                      
│   ├── 📁 Components           # 재사용 가능한 컴포넌트 디렉토리
│   ├── 📁 Navigator            # 화면 간 네비게이션 설정 디렉토리
│   └── 📁 screens              # 각 화면별 컴포넌트 디렉토리
│       ├── 📁 Login            # 로그인 화면 관련 파일
│       ├── 📁 MedicalHistory   # 의료 기록 화면 관련 파일
│       ├── 📁 PatientInfo      # 환자 정보 화면 관련 파일
│       ├── 📁 Reservation      # 예약 화면 관련 파일
│       ├── 📄 Home.js          # 홈 화면 컴포넌트
│       ├── 📄 MedicalHistory.js# 의료 기록 메인 화면
│       ├── 📄 MyPage.js        # 내 정보 화면 컴포넌트
│       ├── 📄 OnboardingScreen.js # 온보딩 화면
│       ├── 📄 Reservation.js   # 예약 메인 화면
│       └── 📄 index.js         # 화면 엔트리 포인트
├── 📁 assets                   # 애플리케이션 정적 자산 디렉토리
│   ├── 📁 fonts                # 폰트 파일 디렉토리
│   └── 📁 images               # 이미지 파일 디렉토리
├── 📁 components               # 재사용 가능한 컴포넌트 디렉토리
│   ├── 📁 __tests__            # 테스트 파일 디렉토리
│   └── 📁 navigation           # 네비게이션 관련 컴포넌트 디렉토리
│       ├── 📄 Collapsible.tsx  # 확장/축소 가능한 UI 컴포넌트
│       ├── 📄 ExternalLink.tsx # 외부 링크 관련 컴포넌트
│       ├── 📄 HelloWave.tsx    # 웨이브 애니메이션 컴포넌트
│       ├── 📄 ParallaxScrollView.tsx # 패럴럭스 스크롤뷰 컴포넌트
│       ├── 📄 ThemedText.tsx   # 테마 적용 텍스트 컴포넌트
│       └── 📄 ThemedView.tsx   # 테마 적용 뷰 컴포넌트
├── 📁 constants                # 상수 값 관련 디렉토리
│   └── 📄 Colors.ts            # 색상 정의 파일
├── 📁 hooks                    # 커스텀 훅 디렉토리
│   ├── 📄 useColorScheme.ts    # 색상 테마 관련 훅
│   ├── 📄 useColorScheme.web.ts # 웹 색상 테마 관련 훅
│   └── 📄 useThemeColor.ts     # 테마 색상 관리 훅
├── 📁 scripts                  # 프로젝트 스크립트 디렉토리
├── 📄 .gitignore               # Git 무시 설정 파일
├── 📄 app.json                 # 앱 설정 JSON 파일
└── 📄 babel.config.js          # Babel 설정 파일
```
