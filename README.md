# Giphy-Application

🚀 **ScreenShot**
-----------
<img width="855" alt="스크린샷 2021-02-24 오전 1 45 18" src="https://user-images.githubusercontent.com/40010002/108876851-017fbd80-7642-11eb-9d2b-dd0717ca1023.png">

⚡️ **Project Structure**
-----------------
<img width="390" alt="스크린샷 2021-02-24 오전 1 39 08" src="https://user-images.githubusercontent.com/40010002/108877072-368c1000-7642-11eb-8e21-dce1c595cf49.png">


💡 **Stack & Libraries** 
--------------------        
- Minimum SDK level 19 / Target API 29
- **Kotlin** based + **Coroutines** for asynchronous.
- **Dagger-Hilt** - dependency injection
- **ViewBinding** - for binding
- **Retrofit2** - REST APIs
- **OkHttp3** - implementing interceptor.   
- **Paging3** - helps load and display pages of data from a larger dataset
- **Glide** - loading images
- **JetPack** 
  - **LiveData** - notify domain layer data to views.
  - **Lifecycle** - dispose of observing data when lifecycle state changes.
  - **ViewModel** - UI related data holder, lifecycle aware.
  - **Room** - construct a database using the abstract layer.
  - **Navigation** - Framework to navigate between targets
- **Flow, StateFlow** - handle asynchronous cold stream & hot stream
- **EventWrapper** - Handles a single event


💎 **Architecture**
-------------------
**MVVM** based + **Repository pattern**
![final-architecture](https://user-images.githubusercontent.com/40010002/108877306-72bf7080-7642-11eb-9048-c6536514052f.png)

📝 **Issue**
------------
계속 개발해야 하는 기능
  - 앱을 나갔다 들어왔을 때 좋아요 버튼 상태유지 X
  - 리사이클러뷰 재사용 문제로 인한 체크박스 상태 해제
  - 페이징 시에 원활하지 못한 화면과 스크롤
