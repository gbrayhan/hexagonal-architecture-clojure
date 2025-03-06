(ns clojure-ddd-hexagonal.application.usecase.adapters.user-adapter
    (:require [clojure-ddd-hexagonal.infrastructure.rest.controllers.adapters.user-adapter :as controllers-adapter]))

(defn convert-input [user-dto]
  "Converts input data (DTO) to the domain entity."
  (controllers-adapter/dto->domain user-dto))

(defn convert-output [domain-user]
  "Converts the domain entity to an output DTO."
  (controllers-adapter/domain->dto domain-user))
