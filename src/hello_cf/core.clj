(ns hello-cf.core
   (:gen-class)
   (:use [ring.adapter.jetty :only [run-jetty]]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello people, this is a Clojure app running with java buildpack!"})

(defn -main [& args]
  (run-jetty handler {:port 8080}))

;(defn -main [& args]
;   (println "Welcome to my project! These are your args:" args))