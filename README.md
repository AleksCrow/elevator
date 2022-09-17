
# Тестовое задание Junior Java Developer

## Лифт в здании

Необходимо написать приложение без UI(консольное), или с минимальным UI.

Использовать ООП и придерживаться принципов SOLID, DRY и ETC.

Здание состоит из n-ое количества этажей, где n - случайное число генерируемое при старте программы в диапазоне 5 <= n <= 20.

На каждом этаже k количество пассажиров, где k - случайное число генерируемое при старте программы в диапазоне 0 <= k <= 10.

Каждый пассажир хочет приехать на определённый этаж отличный от того на котором он находиться.

На каждом этаже есть две кнопки для вызова лифта "Вверх" и "Вниз". исключение составляют нижний и верхний этаж.

Лифт имеет ограничение по вместимости: максимум 5 человек.

Первый раз лифт загружается людьми с первого этажа, и едет от первого(текущего) до наибольшего из тех на которые нужно пассажирам.

По дороге лифт останавливается на тех этажах на которых нужно пассажирам высаживая их и подбирая людей которым нужно в том же направлении в котором движется лифт.

Также если лифт загружен не полностью он может остановиться на этаже на котором есть люди, которым необходимо в том же направлении.

При посадки новых пассажиров максимальный этаж пересчитывается.

Вот пример

Здание из 10 этажей

*Шаг 1*

На первом этаже сели 3 человека:

1-му нужно на 2 этаж;

2-му на 4 этаж;

3-му на 6 этаж;

На данном этапе лифт будет иметь 3-х пассажиров, 2 свободных места и направление в верх до 6 этажа.

*Шаг 2*

Лифт приехал на второй этаж так как одному из пассажиров лифта нужно было на этот этаж.

Пассажир вышел в лифте осталось 2 человека то есть 3 свободных места.

Вышедшему пассажиру назначается новый случайный этаж и он присоединяется к людям которые ждут лифт

На втором этаже 5 человек двум из них нужно вверх одному на 7 другому на 4 этажи

Эти двое садятся в лифт таким образом в лифте 4 человека и одно свободное место. теперь лифт будет иметь направление вверх пока не приедет на 7 этаж так как новому пассажиру нужно на 7 этаж

*Шаг 3*

Лифт движется дальше. на третий этаж никому из пассажиров не нужно но в лифте есть одно свободное место а на третьем этаже четверо из семи людей хотят поехать вверх.

Значит лифт должен остановиться на этаже и подобрать только одного пассажира из тех что хотят ехать вверх по тому как лифт имеет только одно свобоное место.

Далее лифт движется со всеми необходимыми остановками по уже известному алгоритму.

Допустим максимальный этаж на который нужно приехать пользователю не изменился и лифт доехал до 7-го этажа и там высадил всех остающихся в нём пассажиров. Теперь люди на этаже исходя из большинства выбирают кто сядет в лифт. Например, на этаже 6 человек хотят ехать в низ и 3 человека вверх. В лифт сядет 5 из 6 желающих ехать вниз. И лифт двинется вниз по алгоритму описанному ниже.

Старайтесь при написании программы максимально использовать возможности языка.

Все неоднозначости можно трактовать самостоятельно (например, куда ехать дальше, если на некотором этаже вышли все, но больше на этом этаже пассажиров нет).

Для консольной программы вывод сделать покадровый - для каждого перемещения лифта один кадр.

Пример вывода, не обязательно делать именно такой.

![Снимок экрана 2021-12-22 в 12.35.31.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/682fabaa-fcf3-419c-b1db-037d316eeb95/Снимок_экрана_2021-12-22_в_12.35.31.png)
