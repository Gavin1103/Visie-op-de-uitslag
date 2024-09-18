# Samenwerkings Contract

## Namen
 - Gavin
 - Aaron
 - Esa
 - Jasper

## Wekelijkse Contactmomenten
 
- Dinsdag 12:00
- Woensdag 09:00
- Donderdag 15:30
- Vrijdag 09:30

Zorg dat je 5 min voor het contactmoment aanwezig bent voor de standup.

## Richtlijnen besluitvorming

- Als je een beslissing neemt die impact heeft op het hele team overleg je dit met met het hele team.
- De beslissing gaat door als de meeste het erover eens zijn.

## Afspraken m.b.t. Deadlines

- De deadline is 2 dagen voor de sprintreview en je dient dan je werk af te hebben.
- Als je het idee hebt dat het niet af gaat komen probeer je dit 3 dagen voor de deadline aan te geven bij het groepje.
- Een dag voor de sprint review doen we een demo met het team als voorbereiding op de review.

## Concequencies bij het niet nakomen van afspraken

- Bij 1 keer de regels niet nakomen word je aangesproken door het team.
- Als het vaker gebeurd gaan we naar de docent om het te melden.

## Algemene regels

 - Als je te laat komt laat je dit voor het begin van de les weten.
 - Als je afwezig bent laat je dit minnimaal een half uur voor het begin van de les weten en het liefst de dag ervoor.
 - Er word duidelijk gecommuniceerd naar je team door middel van het bijhouden van gitlab en duidelijke commit messages.

## Code rules

### Git commit messages:
"Type - optional scope : description"<br>
Types:<br>
feat: feature<br>
fix: bug fixes<br>
docs: changes to the documentation like README<br>
style: style or formatting change<br>
perf: improves code performance<br>
test: test a feature<br>
chore: maintenance/configuration related<br>

### Soorten branches:
hotfix/<name> : voor bufixes op main branch deze moeten daarna wel doorgezet worden naar develop zodat deze snelle fixes verbetered kunnen worden indien nodig
feature/<issue-number>-<name> : voor alle features die gemaakt worden.

### Merge requests:
Je maakt een merge request van de feature naar de develop branch.

Deze feature wordt door minimaal 1 teamlid gereviewed.

Als dev van een feature ben jij verandwoordelijk om ervoor te zorgen dat jouw feature gereviewed/getest wordt door een teamlid.

Pas wanneer een feature compleet geaccepteerd is wordt deze gemergd naar de develop branch.
Je mag features op features maken.
voorbeeld:
Main feature: feature/new-ui-overhaul
deze branch is voor alle wijzigignen gerelateerd aan de nieuwe UI

Sub-branch: feature/new-ui-homepage
Deze branch takt af van feature/new-ui-overhaul en focust op het UI van de homepage.

Nog een sub-feature branch: feature/new-ui-homepage-add-login
Terwijl feature/new-ui-homepage nog gemaakt wordt, werkt een teamlid aan de inlog op de nieuwe homepage. In plaats van te wachten tot feature/new-ui-homepage klaar is, wordt een nieuwe branch feature/new-ui-homepage-add-login gemaakt die aftakt van feature/new-ui-homepage.

Dit zorgt ervoor dat er gewoon doorgewerkt kan worden, als er maar regelmatig de parent branch gemerged wordt dus bijvoorbeeld als jij in feature/new-ui-homepage-add-login werkt merge je regelmatig met feature/new-ui-homepage

en feature/new-ui-homepage mergt regelmatig feature/new-ui-overhaul in zijn branch.

### Docs:
Documentatie die met het project te maken hebben zetten we op de main branch in de folder documentation/