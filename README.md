# Crypto Analyzer


## Opis

Projekt wykonany na potrzeby kursu informatycznego na Politechnice Wrocławskiej. Celem projektu jest pobieranie danych kryptowalutowych, zapisywanie ich w ustrukturyzowanych formatach oraz wykonywanie analiz.

Aktualnie dostępne są dwie strategie pobierania danych (źródła):
1. Binance
2. Coinbase
3. Pobieranie danych CSV (służy wyłącznie do testów)

Dostępne metody do eksportowania / analizy:
1. JSON
2. CSV
3. XML
4. YAML
5. Ryzyko (plik .txt); strategia ryzyka zależy od zmienności ceny


## Podgląd

### Główny ekran

![](https://github.com/Filipeak/crypto-analyzer/blob/main/assets/mainPreview.png)

### Wykres ceny

![](https://github.com/Filipeak/crypto-analyzer/blob/main/assets/chartPreview.png)


## Architektura

### Diagram UML

![](https://github.com/Filipeak/crypto-analyzer/blob/main/assets/uml.jpg)

### Wzorce projektowe

Projekt został zaprojektowany w oparciu o kilka klasycznych wzorców projektowych, które zwiększają jego modularność, rozszerzalność i łatwość utrzymania. Poniżej znajduje się szczegółowe omówienie ich zastosowania:

**Observer (obserwator)** – użyty w logowaniu i eksporterach. Pozwala na automatyczne powiadamianie zainteresowanych obiektów.
- moduły logowania reagują natychmiast na nowe logi bez konieczności ścisłego powiązania z logiką biznesową
- eksporterzy mogą dynamicznie reagować na aktualizację danych, zapisując je w odpowiednich formatach
- łatwo można dodać nowe komponenty reagujące na zdarzenia bez modyfikacji istniejącego kodu

**Strategy (Strategia)** – został zastosowany w dwóch kluczowych miejscach:
- Źródła danych – różne strategie pobierania danych (np. *Binance*, *Coinbase*, *CSV*) implementują ten sam interfejs, dzięki czemu można je łatwo wymieniać lub dodawać nowe bez zmiany głównej logiki programu.
- Strategie ryzyka – różne metody oceny ryzyka (np. Value at Risk, regresja liniowa) są niezależnymi strategiami, które można dynamicznie wybierać w zależności od warunków rynkowych (np. zmienności).

**Singleton (Pojedynczy)** – wykorzystany w klasach *DataManager* i *Logger*. Celem jest zagwarantowanie, że w systemie istnieje tylko jedna instancja tych klas.

**Dependency Injection (Wstrzykiwanie zależności)** – Zastosowanie Dependency Injection pozwala na luźne powiązanie między komponentami – szczególnie w przypadku eksporterów danych. Zamiast tworzyć obiekty eksportujące bezpośrednio w kodzie, są one wstrzykiwane jako zależności (np. do klasy zarządzającej eksportem - *BufferedWriterCreator*).
Korzyści:
- ułatwione testowanie (można łatwo podmieniać zależności na atrapy)
- lepsza czytelność i modularność
- możliwość łatwej rozbudowy o nowe typy eksporterów (np. PDF, SQL) bez ingerencji w istniejącą logikę.


## Ocena ryzyka

Ryzyko jest szacowane za pomocą dwóch metod, a dana metoda jest wybierana na podstawie zmienności rynku (Volatility):

#### VaR (Value at Risk)

Ta metoda odpowiada na pytanie jaka potencjalna możliwa strata przy pewności 95%.

#### Wykrywanie anomalii za pomocą regresji liniowej

Ta metoda próbuje stworzyć najbardziej pasującą linię na wykresie Zmiana ceny vs Wolumen:

![](https://github.com/Filipeak/crypto-analyzer/blob/main/assets/linearRegression.png)

Potem wszystkie "anomalie", czyli punkty najbardziej oddalone są zliczane i obliczany jest procent do całości.