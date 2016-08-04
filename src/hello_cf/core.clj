(ns hello-cf.core
   (:gen-class)
   (:use [ring.adapter.jetty :only [run-jetty]]))

(System/getProperty "os.version")

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "<h2>Hello people, this is a Clojure app running with buildpack: " (System/getProperty "buildpack.version") "</h2> <br>" (System/getProperty "CF_INSTANCE_INDEX") (newline) "  <h3>VCAP_APPLICATION environment variables:</h3><br>" (System/getenv "VCAP_APPLICATION") )})

(defn -main [& args]
  (run-jetty handler {:port 8080}))
