# MnemeScan
first of all it is an trainings-project.
primary function should be to scan periodically wireless networks in range,
temporary store and hand them to another service for further processing.

# motivation
for another project of mine, I do need data to play around with some "larger" amount of data
all day long I do carry my mobile phone time to get some advantage out of it!

... naaahh what ever, I just need some data! for playtime :-)

# features
## done
* scan periodically all available wireless networks in range
* store scanned data (date/time , SSID , MAC , securrety (open, WEP, WPA, WPA2, aso.) , signal strength , GPS data ... )
* transfere via REST-APT to other service for further processing

## open (prio top to bottom)
* refactore overall architecture
 * depentancy injection (dagger2?) - im worried of memory leak
 * check if background task well done or not
 * decouple services from main activity (feels wrong)
* central exception handling
* error logging (in case app crashed)
* improve user xp
 * enable GPS service (if not enabled) during app start
 * disable GPS service (if before was disabled) during app stop
 * send to processing service function should only be used if connected to home network
 * get rid of manualy triger send to processing service, instead send automatically when connected to home network
* changeable configuration
 * url and port of processing service
 * define/select home network
* think of sync data back from processing service - needed in case of "ups I deleted the app!!!"
* https instead of http
* activity logging (?)
* authentication (?)

# dependancies
* <s>degger2</s> - currently overextended, maybe later during refactoring
* [butterknife](http://jakewharton.github.io/butterknife/) - annotation based view bindings
* [room](https://developer.android.com/topic/libraries/architecture/room.html) - persistence library

# compile and use
**FIXME** - extend build and use commands
´´´
gradle build
´´´
