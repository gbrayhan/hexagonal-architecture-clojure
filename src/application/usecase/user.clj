(ns application.usecase.user
  (:require [domain.user :as d-user]))

(defn create-user [repository domain-user]
  (d-user/create-user repository domain-user))

(defn get-all-users [repository]
  (d-user/get-all repository))

(defn get-by-id [repository id]
  (d-user/get-by-id repository id))

(defn update-user [repository id domain-user]
  (d-user/update-user repository id domain-user))

(defn delete-user [repository id]
  (d-user/delete-user repository id))
