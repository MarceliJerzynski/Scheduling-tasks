
# Opis zadania

Zadanie polegało na utworzenia generatora instancji zadań oraz implementacja algorytmu szeregującego wykorzystującego jedną maszynę, który nie przekraczałby złożoności obliczeniowej O(n^2 log n). Każde zadanie reprezentowane jest przez 5 liczb: indeks zadania, jego moment gotowości (p), moment możliwego rozpoczęcia zadania (r), jego czas, do którego zadanie powinno zostać wykonane (d), w przeciwnym razie zostanie naliczona kara, oraz karę za spóźnienie danego zadania (W)

# Opis generatora instancji

Zaimplementowany generator zakłada, że długość zadania musi zawierać się w przedziale <1,9> z krokiem 1.
Po wygenerowaniu pseudolosowych liczb z tego przedziału w podanej na początku liczności, obliczana jest suma długości czasów zadań oznaczona jako SumP. Liczba r_i danego zadania i jest losowana z przedziału <0, SumP>
 Liczba d_i również jest liczbą pseudolosową, z przedziału <2*p_i + r_i , 4*p_i + r_i> . Zakres ten został wyznaczony empirycznie, głównym założeniem pozwalającym na zaakceptowanie testowanych przedziałów był fakt, że dane zadanie musi mieć możliwość wykonania się, jednak czas nie może być zbyt długi, by problem nie był trywialny. Podany przedział zakłada, że w najbardziej "pesymistycznym" dla zadania przypadku, ma ono 2*p_i czasu, by mogło się wykonać, a więc maksymalne przesunięcie w stosunku do r_i wynosi p_i, zanim zostanie naliczona kara - w "optymistycznym" wynosi ono 3*p_i.
Waga kary za spóźnienie również posiada element pseudolosowy i obliczana jest następująco:
Na początku losowana jest liczba z przedziału <0, p_i+1>. Następnie dodawane do niej jest 28/(d_i - r_i), a następnie ograniczenie z góry na 13. Założenie było następujące: 
Liczba p_i+1 to długość zadania + 1, pozwalające nałożenie większej wagi dla dłuższego zadania. Zadanie o długości 9 jest dłuższe niż zadanie o czasie trwania 1, więc istnieje większa szansa, że zostanie wykonane po czasie d_i np o 1 jednostkę czasu, jeśli autor algorytmu szeregującego nie sprawdzi, czy zadanie na pewno ma czas by się wykonać. Liczba 28 to maksymalne okno czasowe dla najdłuższego możliwego zadania (9*3) plus 1. Ta liczba jest podzielona przez okno każdego zadania. Oznacza to, że im mniejsze jest okno, tym większa jest kara za spóźnienie danego zadania. Zadania o mniejszym oknie są trudniejsze dla algorytmu do uszeregowania, dlatego kara ta jest w takim przypadku większa. Ograniczenie z góry na 13 wynika z obserwacji zbyt dużych wag kar dla wartości skrajnych i pozwala pozbyć się takich "outsiderów"

# Opis algorytmu szeregującego

Algorytm szeregujący w pierwszym kroku sortuje rosnąco zadania zgodnie z wartością d_i ( na początku prac z algorytmem sortowanie następowało po r_i, jednak okazało się że sortowanie po atrybucie d_i pozwala uzyskać o wiele lepszą jakość rozwiązania). Jeśli natomiast wartości te są takie same, obliczany jest współczynnik w_i/p_i i wtedy zadania sortowane są malejąco po tym współczynniku. Zastosowana funkcja to algorym Quicksort, sortujący tablicę ze złożonością średnią O(n* log n), a najgorszą O(n^2).
Po sortowaniu następuje bardziej szczegółowe szeregowanie:
Rozwiązując zadanie zapisywana jest na bieżąco zmienna reprezentująca upływ czasu.
  1 Jeśli aktualny czas nie pozwala na wykonanie zadania pierwszego w posortowanej liście, algorytm czeka, aż nastąpi moment r_i.
  2 Jeśli rozpoczęcia wykonywania zadania w aktualnej chwili sprawi, że dane zadanie nie wykona się przed momentem d_i, dane zadanie i zaliczanie jest do zadań spóźnionych i nie zostaje ono wykonane
	3 Jeśli powyższy warunek pozwoli na wykonanie tego zadania, sprawdzane jest, czy kolejne zadanie z tablicy wykonane od razu po aktualnie rozważanym może się wykonać i nie zostanie naliczona kara.
	4 Jeśli następne zadanie nie będzie możliwe do zrealizowania, sprawdzane jest, które zadanie posiada większą wagę kary - to zadanie będzie wykonane w aktualnej chwili, drugie zostanie zapisane do zadań spóźnionych. 
	5 Gdy wszystkie zadania bez kary będą wykonane, w kolejności dodawania do listy wykonywane są zadania spóźnione

Algorytm opiera się na założeniu, że zadanie które nie ma możliwości wykonania się bez kary po wstępnym sortowaniu, nie powinno być wykonywane dopóki wszystkie zadania, których czas zakończenia nie przekroczył d_i nie wykonają się pierwsze. Rozważany model zakłada, że naliczana kara jest niezależna od wielkości spóźnienia, więc jeśli spóźnienie wynosi 1 jednostkę czasu, nie warto go wykonywać w ogóle, a można skupić się na pozostałych zadaniach. Złożoność algorytmu wynosi O(n*log n + n), co sprowadza się do O(n*log n). Algorytm można rozszerzyć, biorąc pod uwagę, że w założeniu zadania algorytm może mieć złożoność O(n^2*log n), jednak wyniki okazały się być satysfakcjonujące, a zwiększenie złożoności pogorszyłoby czasy oczekiwania na rozwiązanie, które już teraz były poniżej zadowalającego poziomu.
