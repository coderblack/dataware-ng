
case class EventCase(

                      var account: String = null,
                      var carrier: String = null,
                      var deviceId: String = null,
                      var deviceType: String = null,
                      var eventId: String = null,
                      var ip: String = null,
                      var latitude: Double = .0,
                      var longitude: Double = .0,
                      var netType: String = null,
                      var osName: String = null,
                      var osVersion: String = null,
                      var properties: Map[String, String] = null,
                      var resolution: String = null,
                      var sessionId: String = null,
                      var timeStamp: Long = 0L

                    )
