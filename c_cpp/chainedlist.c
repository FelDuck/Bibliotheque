// header section
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
struct box {
	struct box* next;
	struct box* prev;
	double box_data;
	bool endpoint;
};

// program section

int main(){
  //initialisation of first box
  struct box* boxe = (struct box*)malloc(sizeof(struct box));
  boxe->endpoint = true;
  
  //example loading 10 boxes with numbers 1 to 10
  for(int i = 0;i<10;i++){
    printf("%i\n",i);
    struct box* temp = boxe;
    boxe = (struct box*)malloc(sizeof(struct box));
    temp->box_data = i;
    temp->next = boxe;
    boxe->prev = temp;
    boxe->endpoint = false;
  }
  boxe->endpoint = true;
  boxe = boxe->prev;

  //loops through the boxes but will not print last one in this case
  do{
    printf("%f\n",boxe->box_data);
    struct box* temp = boxe;
    boxe = boxe->prev;
    free((void*)temp);
    }while(!boxe->endpoint);
  free((void*)boxe);
}
