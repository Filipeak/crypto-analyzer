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

 - **Observer (obserwator)** – użyty w logowaniu i eksporterach
 - **Strategy (Strategia)** – zastosowany dla źródeł danych oraz różnych strategii ryzyka
 - **Singleton (Pojedynczy)** – wykorzystany w klasach DataManager i Logger
 - **Dependency Injection (Wstrzykiwanie zależności)** – użyte w modułach eksportujących dane


## Ocena ryzyka

Ryzyko jest szacowane za pomocą dwóch metod, a dana metoda jest wybierana na podstawie zmienności rynku (Volatility):

#### VaR (Value at Risk)

Ta metoda odpowiada na pytanie jaka potencjalna możliwa strata przy pewności 95%.

#### Wykrywanie anomalii za pomocą regresji liniowej

Ta metoda próbuje stworzyć najbardziej pasującą linię na wykresie Zmiana ceny vs Wolumen:

![](https://github.com/Filipeak/crypto-analyzer/blob/main/assets/linearRegression.png)

Potem wszystkie "anomalie", czyli punkty najbardziej oddalone są zliczane i obliczany jest procent do całości.