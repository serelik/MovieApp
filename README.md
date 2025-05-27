# 🎬 MovieApp

**MovieApp** — Android-приложение для просмотра и поиска фильмов с использованием [The Movie DB API](https://www.themoviedb.org/).  
Реализовано на Android View с архитектурой MVVM, пагинацией и сохранением избранных фильмов.

---

## 📱 Скриншоты экранов

| Экран | Описание | Превью |
|-------|----------|--------|
| 🎞️ Главный экран | Табы: Popular, Now Playing, Upcoming. Поддержка Paging 3. | <img src="https://github.com/user-attachments/assets/4d964373-7a5d-45ca-9cf6-60ab1e027545" width="200"/> |
| 🎥 Детали фильма | Описание, постер, рейтинг, список актёров | <img src="https://github.com/user-attachments/assets/15581e90-cb37-4421-b40d-5afb57211dcb" width="200"/> |
| 👤 Детали актера | Фото, имя, фильмография | <img src="https://github.com/user-attachments/assets/b876877e-2d71-4e61-8814-46fd230ebfac" width="200"/> |
| ⭐ Избранное | Локально сохранённые фильмы | <img src="https://github.com/user-attachments/assets/dc49593c-5f12-4046-acd2-60aae459f01d" width="200"/> |
| 🔍 Поиск | Поиск фильмов с пагинацией | <img src="https://github.com/user-attachments/assets/2f053d20-9f7e-4585-b828-9bc423f1f937" width="200"/> |

---

## 🔧 Архитектура и технологии

- **MVVM** (Model-View-ViewModel)
- **Hilt (DI)** — внедрение зависимостей
- **Retrofit + OkHttp** — сетевые запросы
- **Paging 3** — постраничная подгрузка фильмов
- **Room** — хранение избранных фильмов
- **LiveData + ViewModel** — управление состоянием UI
- **Navigation Component** — переходы между экранами

---

## 📦 Экраны приложения

### 🏠 Главный экран
- Список фильмов с табами:
  - `Popular`
  - `Now Playing`
  - `Upcoming`
- Реализована пагинация с `Paging 3`
- Поддержка swipe-to-refresh

### 🎬 Детали фильма
- Информация о фильме
- Список актеров
- Кнопка "в избранное"

### 👥 Детали актера
- Фото, имя, биография
- Список фильмов с участием

### ⭐ Избранное
- Список фильмов, сохранённых через Room
- Возможность удалить из избранного

### 🔍 Поиск
- Поиск фильмов по названию
- Paging 3
- Отображение результата в `RecyclerView`

---
