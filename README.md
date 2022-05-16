# StarWarsSage
![ic_splash_img](https://user-images.githubusercontent.com/79571688/168622593-0a25b735-1dd5-4e7a-a031-6886247081ea.png)

Выполнение тестового задания по получению данных по фильмам вселенной Звёздных Войн от сервера https://swapi.dev.
Сохранение данных в базу данных телефона (Room Database). Отображение экранов со списком фильмов, персонажей выбранного фильма, и планеты рождения выбранного персонажа.


Проект выполнен по архитектуре MVVM. Стек: kotlin, hilt, retrofit+okhttp, coil, cicerone, livedata, room database.

Готовый функионал:

1. Splashscreen, шиммер на время загрузки данных от сервера, показ списка фильмов, поиск по фильмам:

  ![main_screen](https://user-images.githubusercontent.com/79571688/168627301-04c00af0-d2d2-4905-973d-21e21282a9ed.gif)

2. Зум картинки:

![zoom_image](https://user-images.githubusercontent.com/79571688/168627883-8f3c5337-6334-41b5-9156-743b082cdb57.gif)

3. Переход на экран персонажей фильма, и отображение статуса загрузки в виде кастомных тостов:
