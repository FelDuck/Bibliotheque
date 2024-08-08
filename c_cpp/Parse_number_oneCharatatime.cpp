
//will work well in the case where parsing CSV or any file like that
void fonction(){
			eof_not_reached = true;
			while(eof_not_reached) {
				int_char = fgetc(fichier);
			switch(int_char) {
				case 48: case 49 : case 50 :case 51:case 52:case 53:case 54:case 55:case 56:case 57:case 46: //0 a 9 et '.'
				str.AppendChar((char)int_char);
				break;
				case 10: {// '/n'
					//save info
					//convert string to number
					//reset string
				}
			
				break;
				case -1: // end of file
				eof_not_reached = false;
				break;
				}
			}
