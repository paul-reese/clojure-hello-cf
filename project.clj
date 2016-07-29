(defproject hello-cf "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "Hello Clojure on Cloud Foundry!"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.1.8"]
                 [ring/ring-jetty-adapter "1.1.8"]]
  :profiles {:dev {:dependencies [[ring/ring-devel "1.4.0"]]}}
  :main hello-cf.core
  :aot [hello-cf.core])
