# Wypożyczalnia nart

Autor: Artur Kręgiel

## Uruchomienie

Program może działać w jednym z dwóch trybów:

- tekstowym
- graficznym

### Tryb tekstowy

Sposób uruchomienia wygląda następująco:

```
$ java -jar lab02.jar preferences.txt wykaz.txt znizki.txt
```

### Tryb graficzny

Jeśli uruchomimy program bez argumentów, program będzie działał w trybie grafycznym

```
$ java -jar lab02.jar
```

## Przykład poprawnych danych

### Plik z preferencjami

```
1,C,slalom:169
2,J,carving:129;carving:195;gigant:162
3,A,slalom:100;allmountain:164
4,J,carving:100;allmountain:162
```

> Typy nart muszą być pisane małymi literami (*lowercase*)

### Plik z wykazem

```
1,carving:130
3,slalom:120
1,gigant:146
4,slalom:167
```

### Plik ze zniżkami

```
C,50
J,20
A,0
```

## Generowanie plików danych

Do generowania plików z preferencjami nart oraz wykazem służy skrypt w języku Python o nazwie `generate_data.py`.

