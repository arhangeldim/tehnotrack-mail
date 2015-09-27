## Цикл разработки Github

### Создание репозитория

1. Зарегистрируйтесь на github.com
2. Создайте новый репоизотрий (Вкладка Repositories -> New)
3. Склонируйте созданный репозиторий себе на машину ( git clone <адрес репозитоия> )


### Проверка
Теперь у вас есть папка с именем репозитория, в ней дожна быть скрытая папка .git
попробуйте также ввести команду

> $ git status

И git ответит, что он в ветке master и пока никаких изменений нет

```
On branch master
nothing to commit, working directory clean
```

Создаем новый пустой файл **README.md** и добавляем туда описание проекта. Можно писать простым текстом, можно посмотреть, что такое формат .md - Markdown https://help.github.com/articles/github-flavored-markdown/
В IntellijIDEA есть плагин для markdown разметки

> $ git status

```
On branch master
Untracked files:
  (use "git add <file>..." to include in what will be committed)

	README.md

nothing added to commit but untracked files present (use "git add" to track)
```

Добавляем в index:

> $ git add README.md

```
On branch master
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

	new file:   README.md
```

и комитим (сохранем состояние). С помощью ключа -m добавляем описание изменений

> git commit -m "Added README file"

Отправляем изменения на сервер (текущая ветка)

> $ git push origin HEAD


## Разработка в ветках

[что такое ветка?](https://git-scm.com/book/ru/v1/%D0%92%D0%B5%D1%82%D0%B2%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B2-Git-%D0%A7%D1%82%D0%BE-%D1%82%D0%B0%D0%BA%D0%BE%D0%B5-%D0%B2%D0%B5%D1%82%D0%BA%D0%B0%3F)
[разработка в ветках](http://habrahabr.ru/post/192614/) 

 Используется две ветки кода - master (основная) и dev (текущая разработка). В master ветке должен быть только код, который прошел код-ревью (проверку преподавателем). В dev ветке
 можно вносить любые изменения.
 
 Шаги разработки.
 
 **Создание ветки**
 К вам приходит новая задачка и вы начинаете работу над ней. Для работы над задачкой нужно завести новую ветку. Ветку стоит именовать осмысленно, например, задача написать клиентскую авторизацию
  Для этого создадим ветку ClientAuthorization
 
 
```
 # Переключимся на мастер, если вдруг мы были не там
 git checkout master
 
 # Обновимся до последней версии
 git pull
 
 # И переключимся на ветку ClientAuthorization. Ключ -b говорит, чтобы перед переключением git ее создал
 git checkout -b ClientAuthorization
 
 # Статус нам скажет On branch ClientAuthorization
 git status
 
 # Также попробуйте посмотреть команду
 git branch
```

 **Теперь все изменения по задаче делаются только в этой ветке!**
    
 **Отправка изменений на сервер**
 
 Когда вы считаете, что задача закончена, делайте комит и заливайте изменения в репозиторий
    
> $ git commit -m "..."

> $ git push origin HEAD
   
  У меня примерно так: 
```
   arkhangelskiy:tehnotrack-mail dmirty$ git push origin HEAD
   Counting objects: 3, done.
   Delta compression using up to 8 threads.
   Compressing objects: 100% (2/2), done.
   Writing objects: 100% (3/3), 324 bytes | 0 bytes/s, done.
   Total 3 (delta 0), reused 0 (delta 0)
   To https://github.com/arhangeldim/tehnotrack-mail.git
      385be4a..1f66dc3  HEAD -> ClientAuthorization
``` 

### код ревью (pull request)
    
[Хелп](https://help.github.com/articles/using-pull-requests/) 

В интерфейсе github в вашем репозитории раздел branches, далее выбрать нужную ветку и нажать Open [скрин](https://www.dropbox.com/s/vowip78gjiu5de2/github_pullrequest.png?dl=0)
    
Добавьте меня в созданный pull request, чтобы я смог оставлять комментарии. Все, что потребуется поправить, нужно будет править в этой ветке, после push изменения автоматически попадут в pullRequest.

После того, как все будет ОК, нажмите кнопку Merge и изменения попадут в вашу основную master ветку