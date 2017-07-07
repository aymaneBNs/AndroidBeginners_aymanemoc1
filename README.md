# AndroidBeginners_aymanemoc1
my final Project( androidBeginners)

hello

this is some important informations about my application
- I use a listeView with simple ArrayAdapter To Display the initial information (question,answer) 
- for the flashCard Questions I use A RecyclerView With RecyclerViewAdapter
- I have 2 activity one to show the card and initial info and the other to display a selected question with the answer
- I use a dialogue to add new card with verification (if question or answer is empty)
- Card are stored in DB named "FlashCard.db"
-I used Content Provider with loader  to access to Db and display question 
- My initial information File stored in assets/initial_information.json
- My application contain a widget to show a random questions and display the answer of them if the user click ShowAnswer
-I use an AsyncTask to get initial information to display them in the Main
-I use an other  AsyncTask called InitialInformationTaskForWidget to get initial information from Json and Display them in the widget


