(ns organizer.views
  (:require [re-frame.core :as re-frame]))

(defn render-list [{:keys [event time]}]
  [:li "Event: " event " Time: " time])



(defn second-selection [on-change]
  [:select {:on-change on-change} 
    (for [s (range 60)]
      [:option {:value s} s])])

(defn minute-selection [on-change]
  [:select {:on-change on-change} 
    (for [m (range 60)]
      [:option {:value m} m])])


(defn hour-selection [on-change]
  [:select {:on-change on-change} 
    (for [h (range 24)]
      [:option {:value h} h])])

(defn day-selection [on-change]
  (let [days (range 1 32)]
   [:select {:on-change on-change}
   (for [d days]
     [:option {:value d} d])]))

(defn month-selection [on-change]
  (let [months (range 1 13)]
    [:select {:on-change on-change}
     (for [m months]
      [:option {:value m} m])]))

(defn year-selection [on-change]
  (let [years (range 2017 2025)]
    [:select {:on-change on-change}
     (for [y years]
      [:option {:value y} y])]))

(defn create-form []
  (let [cur-time {:year (.getFullYear (js/Date.))
                  :month (.getMonth (js/Date.))
                  :day (.getDate (js/Date.))
                  :hour (.getHours (js/Date.))
                  :minute (.getMinutes (js/Date.))
                  :second (.getSeconds (js/Date.))}
        input (atom {:event "" :time cur-time})]
   [:div {} 
    "Event:" 
    [:br]
    [:input {:type "text" :name "event" 
             :on-change #(swap! input assoc :event (-> % .-target .-value))}]    
    [:br]
    "Year: "
    [year-selection #(swap! input assoc-in [:time :year] (-> % .-target .-value))]
    "Month: "
    [month-selection #(swap! input assoc-in [:time :month] (-> % .-target .-value))]
    "Day: "
    [day-selection #(swap! input assoc-in [:time :day] (-> % .-target .-value))]
    "Hour: "
    [hour-selection #(swap! input assoc-in [:time :hour] (-> % .-target .-value))]
    "Minute: "
    [minute-selection #(swap! input assoc-in [:time :minute] (-> % .-target .-value))]
    "Second: "
    [second-selection #(swap! input assoc-in [:time :second] (-> % .-target .-value))]
    [:br]
    [:input {:type "submit" :on-click #(re-frame/dispatch [:add-event @input]) }]])
  )


(defn main-panel []
  (let [todo (re-frame/subscribe [:todo])]
    (fn []
      (println @todo)
      [:div [:ul (for [event @todo] (render-list event))] 
       [create-form]]
      )))
