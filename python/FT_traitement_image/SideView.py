import cv2 as cv
import numpy as np
import ball
import decimal


# utilise cette video : https://drive.google.com/file/d/1M7RCivrssEYUnbZh3tewvX8_eNs6uUhm/view?usp=drive_link
# Avec peu de modification ce script python devrait etre capable de detecter un projectile a partir d'une video vue de coter meme si
# l'image est brouiller avec beaucoup de bruit


HUMAN_SZ = 75
ESTIMATE_AIR_DRAG = .85
BATPOS = 505
ERROR_MARGIN = 10
DETECT_ZONE = 135
MARGIN_RED_LEFT = 20
MARGIN_RED_RIGHT = 50

def loadframe(name:str):
    #fonction prenant une video et capture toute les frame a l'interieur d'une liste
    frames = list()
    cap = cv.VideoCapture(name)
    while cap.isOpened():
        ret, frame = cap.read()
        if not ret:
            cap.release()
            return frames
        frames.append(frame)
    raise ConnectionAbortedError

def chooseFrame(name:str,fct=None,fct2=None):
    # defille au travers des frames avec un certain controle
    # essentiellement un outils utiliser lors de la conception

    index = 0
    nindex = 0
    red = 0
    frames = loadframe(name)

    while True:
        frame = frames[index]

        if not fct is None:
            fram = fct2(frame,frames[index+1])
            cv.imshow("frame fcts", fram)

        if not fct2 is None:
            frame2 = fct2(frame,frames[index+1])
            cv.imshow("frame fcts2", frame2)

        for i in range(0,len(frame)):
            frame[i][red] = np.array([0, 0, 255])
        for i in range(0,len(frame[0])):
            frame[nindex][i] = np.array([0, 255, 0])

        cv.imshow(name, frame)
        key = cv.waitKey(1)

        if key == ord("q"):
            return( index )
        elif key == ord("a"):
            index -= 1
            print( index )
        elif key == ord("d"):
            index += 1
            print( index )
        elif key == ord("c"):
            nindex -= 1
            print( nindex )
        elif key == ord("z"):
            nindex += 1
            print( nindex )
        # retourne le centre du spot rouge qui est moi
        elif key == ord("r"):
            #red = getRd_x( frame )
            print(red)


def write_video(nom:str,frames:list,fps,low_index=0,high_index=None,format='mp4v',sz=None):
    # Enregistre une video avec certain parrametres pas utiliser pour l'algorythme
    if high_index==None: high_index = len(frames)
    if sz == None: sz = (len(frames[0]),len(frames[0][0]))

    forma = cv.VideoWriter.fourcc(format[0],format[1],format[2],format[3])
    out = cv.VideoWriter(nom, forma, fps, sz)
    for i in range(low_index, high_index):
        out.write(frames[i])
    out.release()

def mask_speed(frame1,frame2):
    # Fonction prenant deux frame et se servant de la division et l'addition de deux frames succinte

    # Masque pour le gris
    lower = np.array([100, 100, 100])
    upper = np.array([255, 255, 255])
    frame1 = cv.inRange(frame1, lower, upper)
    frame2 = cv.inRange(frame2, lower, upper)

    # detecte la balle par le changement de valeur de pixel
    kernel1 = np.array([[0, -1, -1, 0], [-1, 1, 1, -1], [-1, 1, 1, -1], [0, -1, -1, 0]]).astype(np.uint8)
    kernel2 = np.array([[0, -1, 0, ],[-1, 1, -1],[0, -1, 0, ]]).astype(np.uint8)
    kernel3 = np.ones((3, 3), np.uint8)

    # voir la diffference au niveau des pixels dune frame a l'autre
    frame1 = (frame1 / 2) + (frame2 / 2)
    frame1 = cv.inRange(frame1 ,100 ,250)

    # Filtres morphologiques pour esseyer d'enlever le plus de noise possibles (presque impossible les images suces)
    # HIT_MISS detecte un paterne etablit par le kernel 1 et 2

    frame1 = cv.morphologyEx(frame1, cv.MORPH_HITMISS, kernel1)
    frame1 = cv.morphologyEx(frame1, cv.MORPH_HITMISS, kernel2)
    frame1 = cv.morphologyEx(frame1, cv.MORPH_DILATE, kernel3)

    return frame1

def detect_side_ball(name:str):
    # nerf de la guerre de la detection de l'image de coter

    nFrame = 0                      #Numero de frame
    nBall = 0                       #Nombre de balle detecter et confirmer
    confirmed = False               #Instantiation de la varibale trackant si la balle est confirmer ou non
    framedict = {}                  #Instantiation de la variable de retour ayant le format {nBall: [numero_de_frame,% highbound,%lowbound]} -> plus de detail fcts getHeight
    meaby_ball = list()             #Instantiation de la liste de balle potentielle
    to_bat = -1                     #Variable servant a passer ne pas analyser certaine frame qui n'ont pas de balle puisque la balle a deja ete trouver
    this_frames_balls = list()      #"Balle" dans la frame c'est probablement du bruit pour la pluspart
    #frames = list( ) #debug

    cap = cv.VideoCapture(name)
    ret, frame_old = cap.read()

    if not ret:
        cap.release()
        raise InterruptedError

    while cap.isOpened():

        if confirmed: confirmed = False
        #if nFrame >= 630: # debug
        #   pass
        ret, frame_new = cap.read()
        if not ret:
            cap.release()
            #chooseFrame(frames,"review bad frames") #debug
            return framedict

        mask = mask_speed(frame_old, frame_new)
        contours, _ = cv.findContours(mask, cv.RETR_LIST, cv.CHAIN_APPROX_SIMPLE)

        for i in contours: #verifie tout les contours pour des balles possibles
            M = cv.moments(i)

            if M['m00'] != 0:
                cx = int(M['m10'] / M['m00'])
                cy = int(M['m01'] / M['m00'])
                if cx < BATPOS: this_frames_balls.append([cx, cy])

        for Ball in meaby_ball: # verifie chacune des balles pour une balle confirmer
            temp = this_frames_balls
            confirmed = Ball.is_this_you(temp,nFrame)
            if confirmed:
                to_bat = Ball.toBat()
                print("loop: ", to_bat)
                framedict[nBall] = [to_bat]  # avait plus de valeur avant gutter pour ceci
                nBall += 1
                meaby_ball.clear()
                break

        if not confirmed: # ajoute une balle a la liste de balle a verifier
            for pos in this_frames_balls:
                if pos[0] < DETECT_ZONE:
                    meaby_ball.append(ball.Ball(this_frames_balls, nFrame))
                    print("newball")
                    break
        frame_old = frame_new
        this_frames_balls.clear()
        nFrame += 1
    raise InterruptedError

if __name__ == '__main__':
    dictio = detect_side_ball("Ecote.mp4")
    print(dictio)


#Ncap = "devant.mp4"
#Ncap = "Ecote.mp4"
#dictio = detect_side_ball(Ncap)
#print(dictio)
#chooseFrame("lol",fct2 = mask_speed)
cv.destroyAllWindows()
