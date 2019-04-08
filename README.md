# Assignment @ VOI

### Some assumptions

* I don't use caching - the app is simple enough. Yet architecture should allow me to add caching easily if required. 
* I decided to make two screens for showcasing Navigation component from Jetpack (one of the best APIs Google ever made, really like it!)
* I am exchanging tokens on client side - this should be done by backend (event Spotify recommends that)
* OkHttp client is not shared between two instances of Retrofit services
* Not handling potential exception when no browser exists to authenticate user
* generateHeaderSignature is part of the AppStorage class - should be moved somewhere.
