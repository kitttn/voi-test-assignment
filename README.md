# Assignment @ VOI

### Some known issues

* I don't use caching - the app is simple enough. Yet architecture should allow me to add caching easily if required. 
* I am exchanging tokens on client side - this should be done by backend (even Spotify recommends that)
* Not handling potential exception when no browser exists to authenticate user
* generateHeaderSignature is part of the AppStorage class - should be moved somewhere.

* I don't check text widths in recyclerview - this should be done, of course
* Not running DiffUtil on recyclerview update - i actually create new fragment instance every time, but still can be an improvement for dynamic content loading (like paging, which isn't done as well)
* Empty list is not handled (explicitly), but not crashing.
* After first launch app works, but for some reasons fragment input is destroyed after returning from search results. I guess its somehow related to re-launch of the activity from deep link. Please help me with this one. 

### Architecture overview

This is a super simple MVVM-coroutine based architecture. I don't actually have model here, because overall logic is super-simple, and coroutines give me additional ability to maintain smaller code base. 
Views are controlled by LiveData, which are populated from the ViewModels. 
First time you launch the app, you will be redirected to the browser to authenticate against Spotify. After you get token, you will be able to perform queries on artists. 

Everything is divided into three modules.
common - mostly used for declaring common dependencies between modules. This should be extended in the future to have common code as well.
app - main app, contains fragments and viewmodels. I decided to make two screens for showcasing Navigation component from Jetpack (one of the best APIs Google ever made, really like it!)
network - everything related to network. I use two Retrofit services here, for authentication and queries. 
