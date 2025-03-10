(ns infrastructure.rest.routes
  (:require [reitit.ring :as ring]
            [infrastructure.rest.controllers.user :as controller]
            [infrastructure.repository.user]))

(def repository-user nil)

(def raw-routes
  (ring/ring-handler
    (ring/router
      [["/user"
        {:get  (partial controller/get-all-handler repository-user)
         :post (partial controller/create-user-handler repository-user)}]
       ["/user/:id"
        {:get    (partial controller/get-by-id-handler repository-user)
         :put    (partial controller/update-user-handler repository-user)
         :delete (partial controller/delete-user-handler repository-user)}]])
    (ring/create-default-handler)))
