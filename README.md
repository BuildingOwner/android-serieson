# Android-SeriesOn

안드로이드 영화 정보 애플리케이션 프로젝트입니다. 영화 데이터를 불러와 인기 영화, 평점 높은 영화, 장르별 영화 등을 보여주고 상세 정보를 제공합니다.

## 프로젝트 개요

이 애플리케이션은 영화 정보를 제공하는 안드로이드 애플리케이션으로, CSV 파일에서 영화 데이터를 불러와 다양한 카테고리로 사용자에게 보여줍니다. 영화 포스터, 상세 정보, 리뷰 등을 확인할 수 있으며, 유사한 영화 추천 기능도 제공합니다.

## 주요 기능

- **메인 화면**:
  - 슬라이더를 통한 인기 영화 표시
  - 인기도 기준 영화 목록
  - 평점 기준 영화 목록
  - 장르별 영화 목록 (액션, 스릴러, 드라마)
  - 검색 기능

- **영화 상세 화면**:
  - 영화 기본 정보 (제목, 감독, 출연진, 개봉 연도 등)
  - 영화 상세 정보 (장르, 런타임, 국가, 줄거리 등)
  - 관련 영화 추천 (같은 출연진이 등장하는 다른 영화)
  - 리뷰 작성 및 확인 기능

- **검색 기능**:
  - 영화 제목을 통한 검색
  - 검색 결과 목록 표시

## 주요 클래스 및 구조

### 모델 클래스

- **Movie.java**: 영화 정보를 담는 모델 클래스
  - 영화 ID, 제목, 개봉년도, 감독, 출연진, 장르, 줄거리 등의 정보 포함
  - Serializable 인터페이스 구현으로 Activity 간 데이터 전달 가능

- **Review.java**: 영화 리뷰 정보를 담는 모델 클래스

### 컨트롤러 클래스

- **MovieController.java**: 영화 데이터 관리 컨트롤러
  - CSV 파일에서 영화 데이터 읽기 (readMoviesFromCSV)
  - 인기도 기준 정렬 (getPList)
  - 평점 기준 정렬 (getVAList)

- **ReviewController.java**: 리뷰 데이터 관리 컨트롤러

### 어댑터 클래스

- **MovieAdapter.java** / **MovieAdapter2.java**: RecyclerView 어댑터
  - 영화 목록 표시를 위한 어댑터

- **SliderPagerAdapter.java**: ViewPager 어댑터
  - 메인 화면의 슬라이더 구현을 위한 어댑터

### 액티비티 클래스

- **MainActivity.java**: 애플리케이션 메인 화면
  - 슬라이더, 카테고리별 영화 목록 표시
  - 장르별 영화 필터링 기능
  - 영화 클릭 이벤트 처리

- **MovieDetailActivity.java**: 영화 상세 정보 화면
  - 영화 상세 정보 표시
  - 리뷰 작성 및 관련 영화 표시

- **SearchActivity.java**: 영화 검색 화면
  - 영화 제목 검색 기능

## 사용된 기술 및 라이브러리

- **JSoup**: 웹 스크래핑 라이브러리 (HTML 파싱)
- **RecyclerView**: 목록 데이터 표시
- **ViewPager**: 슬라이더 구현
- **Spinner**: 장르 선택 드롭다운
- **ActivityOptions**: 화면 전환 애니메이션
- **TabLayout**: 인디케이터 구현

## 주요 기능 구현 설명

### 데이터 로딩

애플리케이션은 외부 저장소(/sdcard/data.csv)에서 CSV 파일을 읽어와 영화 데이터를 로드합니다. MovieController 클래스의 readMoviesFromCSV() 메서드가 이 작업을 담당합니다.

### 영화 정렬 및 필터링

- **인기도 기준 정렬**: PopularityComparator를 사용하여 영화 목록을 인기도 기준으로 정렬
- **평점 기준 정렬**: VoteAverageComparator를 사용하여 영화 목록을 평점 기준으로 정렬
- **장르별 필터링**: 메인 화면의 스피너를 통해 장르(액션, 스릴러, 드라마)별로 영화 목록 필터링

### 화면 전환 및 데이터 전달

- 영화 항목 클릭 시 Movie 객체를 Serializable로 전달하여 상세 화면으로 이동
- ActivityOptions를 사용하여 공유 요소 전환 애니메이션 구현

### 관련 영화 추천

같은 출연진이 등장하는 다른 영화를 찾아 관련 영화로 추천해주는 알고리즘이 MainActivity와 MovieDetailActivity에 구현되어 있습니다.

## 리뷰 기능

### 리뷰 크롤링 및 관리

- **JSoup 라이브러리**: 프로젝트는 JSoup을 사용하여 웹사이트에서 영화 리뷰 데이터를 크롤링합니다.
- **ReviewController.java**: 리뷰 데이터를 크롤링하고 관리하는 컨트롤러 클래스
  - 리뷰 데이터를 수집하고 파싱하는 기능 제공
  - 영화 ID 기준으로 관련 리뷰 검색 및 필터링 기능 제공
- **Review.java**: 리뷰 정보를 담는 모델 클래스
  - 리뷰어 정보, 평점, 리뷰 내용, 작성 날짜 등의 정보 포함

### 주요 리뷰 관련 기능

- **리뷰 표시**: MovieDetailActivity에서 영화별 리뷰 목록 표시
- **리뷰 작성**: 사용자가 영화에 대한 새 리뷰를 작성할 수 있는 기능
- **리뷰 정렬**: 최신순, 평점순 등으로 리뷰 목록 정렬 기능

### 리뷰 크롤링 구현

애플리케이션은 인터넷 권한을 활용하여 웹사이트에서 영화 리뷰 데이터를 크롤링합니다. JSoup 라이브러리를 사용하여 HTML 문서를 파싱하고, 필요한 정보만 추출하여 앱 내에서 사용합니다. 크롤링한 데이터는 앱 내에서 캐싱되어 오프라인 상태에서도 일부 기능을 사용할 수 있습니다.

이 기능을 통해 사용자는 항상 최신 리뷰 데이터를 확인할 수 있으며, 다양한 소스에서 수집된 리뷰를 한 곳에서 볼 수 있는 장점이 있습니다.

## 권한 요청

애플리케이션은 다음 권한을 요청합니다:
- 외부 저장소 읽기/쓰기 권한 (`READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`)
- 인터넷 접속 권한 (`INTERNET`)

## 시작하기

1. 프로젝트 클론
```
git clone https://github.com/BuildingOwner/android-serieson.git
```

2. Android Studio에서 프로젝트 열기
3. CSV 파일 준비 (`/sdcard/data.csv`에 영화 데이터 CSV 파일 준비)
4. 프로젝트 빌드 및 실행

## 데이터 CSV 파일 형식

CSV 파일은 다음 열을 포함해야 합니다:
1. ID
2. TMDB ID
3. IMDB ID
4. 제목
5. 언어
6. 대사 언어
7. 장르 (배열 형식 "[Genre1, Genre2, ...]")
8. 런타임 (분)
9. 개봉일 (YYYY-MM-DD 형식)
10. 인기도 (실수)
11. 평점 (실수)
12. 투표 수 (정수)
13. 감독
14. 출연진 (배열 형식 "['Actor1', 'Actor2', ...]")
15. 태그
16. 줄거리
17. 국가 (쉼표로 구분)

## 주의사항

- 이 애플리케이션은 외부 저장소의 CSV 파일에 의존하므로, 실행 전 적절한 형식의 CSV 파일을 준비해야 합니다.
- Android 11 이상에서는 저장소 권한 정책 변경으로 인해 `requestLegacyExternalStorage="true"` 설정이 필요합니다.
