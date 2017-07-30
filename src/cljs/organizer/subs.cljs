(ns organizer.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))


(re-frame/reg-sub
  :todo
  (fn [db]
    (:todo db)))

(re-frame/reg-sub
  :validation
  (fn [db]
    (:validation db)))

