(ns hello-cf.core
   (:gen-class)
   (:use [ring.adapter.jetty :only [run-jetty]]))

(System/getProperty "os.version")

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "Hello people, this is a Clojure app running with buildpack: " (System/getProperty "buildpack.version"))})

(defn -main [& args]
  (run-jetty handler {:port 8080}))