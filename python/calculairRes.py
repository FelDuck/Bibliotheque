import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import RK45

class ResAir():
    """
    Calcule la trajectoire d'un projectile soumis a la resistance de l'air
    """
    def __init__(self,R,C,p,m,vix,viy):
        
        self.R   = R
        self.C   = C
        self.p   = p
        self.m   = m
        self.vix = vix
        self.viy = viy
        
    
    
    def Range_Kutta(self,):
        def f(t,r,R = self.R,C = self.C,p = self.p, m = self.m):
            """ fonction a utiliser pour le calcule RK"""
            x, y, dx, dy = r
            dx2 = - ((np.pi*(R**2)*p*C)/(2*m))*dx*((dx**2+dy**2)**(1/2))
            dy2 = -9.807 - ((np.pi*(R**2)*p*C)/(2*m))*dy*((dx**2+dy**2)**(1/2))
        
            return np.array([dx,dy,dx2,dy2])
    
        RK = RK45( # Initialisation du calcul RK
        fun = f,
        t0=0, 
        y0=[0, 0,self.vix ,self.viy],
        t_bound=1000,
        rtol=1e-12,
        atol=1e-10,
        )
        """ Serviront a receuillir les donnes du calcul RK """
        t = []
        r = []
        
        while RK.status == "running":
            t.append(RK.t)
            r.append(RK.y)
            RK.step()
        
        t = np.array(t)
        r = np.array(r)
        X = r[:,0]
        Y = r[:,1]
        
        """ Enleve la portion y negative """
        index = []
        for i in range(len(Y)):
            if Y[i]<0:
                index.append(i)
        Y = np.delete(Y,index)
        X = np.delete(X,index) 
        
        return X,Y
class sans_air():
    
    """ 
    Classe qui sert a calculer la trajectoire d'un projectile quand la resistance
    de l'air est negliger   
    """
    
    def __init__(self,xi,yi,vix,viy):
        self.xi  = xi
        self.yi  = yi
        self.vix = vix
        self.viy = viy
        
    def portee(self,t = 100, g = -9.807 ):
        """ calcule de la trajectoire """
        T = np.linspace(0,t,100*t)
        X = self.xi + self.vix*T
        Y = 0.5*g*(T**2) + self.viy*T + self.yi
        
        """ Enleve la portion y negative """
        index = []
        for i in range(len(Y)):
            if Y[i]<=0:
                index.append(i) 
    
        Y = np.delete(Y,index)
        X = np.delete(X,index)
        
        return X,Y



def main():
    
    v =100
    theta = 30*(np.pi/180)
    vix = v*np.cos(theta)
    viy = v*np.sin(theta)
    
    
    A = ResAir(0.08, 0.47, 1.22, 1, vix, viy)
    Xa,Ya = A.Range_Kutta()
    
    
    
    """ controle du graphique de la trajectoire """
    
    plt.figure(figsize=(8,5))
    
    plt.title("Trajectoire d'un boulet de canon avec la resistance de l'air")
    
    plt.xlabel("portee(m)")
    plt.ylabel("hauteur(m)")
    
    plt.plot(Xa,Ya)
    plt.tight_layout()
    
    plt.savefig("trajectoire.png",dpi=600)
    plt.clf()
    
    
    
    """ controle du graphique de comparaison sans resistance de l'air """
    
    B = sans_air(0,0,vix,viy)
    Xb,Yb = B.portee()
    
    plt.figure(figsize=(8,5))
    
    plt.title("Difference de la trajectoire quand la resistance de l'air est negliger")
    
    plt.xlabel("portee(m)")
    plt.ylabel("hauteur(m)")
    
    plt.plot(Xb,Yb,label="Sans resistance de l'air")
    plt.plot(Xa,Ya,label="Avec resistance de l'air" )
    plt.legend(loc="upper right")
    plt.tight_layout()
    
    plt.savefig("NoAirvsAir.png",dpi=600)
    plt.clf()

    
    
    
    """ controle du graphique de l'influence de la masse """
    
    B = ResAir(0.08, 0.47, 1.22, 2, vix, viy)
    Xb,Yb = B.Range_Kutta()
    
    C = ResAir(0.08, 0.47, 1.22, 0.5, vix, viy)
    Xc,Yc = C.Range_Kutta()
    
    
    plt.figure(figsize=(8,5))
    
    plt.title("Influence de la masse sur la portee")
    
    plt.xlabel("portee(m)")
    plt.ylabel("hauteur(m)")
    
    plt.plot(Xb,Yb,label="m = 2")
    plt.plot(Xa,Ya,label="m = 1")
    plt.plot(Xc,Yc,label="m = 1/2")
    plt.legend(loc="upper right")
    plt.tight_layout()
    
    plt.savefig("masse.png",dpi=600)
    plt.clf()
    
    
    
    
    
    """ controle du graphique de l'influence de l'angle de tir avec resistance de l'air """
    
    theta = 45
    C = ResAir(0.08, 0.47, 1.22, 1, v*np.cos(theta*(np.pi/180)), v*np.sin(theta*(np.pi/180)))
    Xc,Yc = C.Range_Kutta()
    
    theta = 40
    B = ResAir(0.08, 0.47, 1.22, 1, v*np.cos(theta*(np.pi/180)), v*np.sin(theta*(np.pi/180)))
    Xb,Yb = B.Range_Kutta()
    
    theta = 35
    D = ResAir(0.08, 0.47, 1.22, 1, v*np.cos(theta*(np.pi/180)), v*np.sin(theta*(np.pi/180)))
    Xd,Yd = D.Range_Kutta()
    
    theta = 25
    E = ResAir(0.08, 0.47, 1.22, 1, v*np.cos(theta*(np.pi/180)), v*np.sin(theta*(np.pi/180)))
    Xe,Ye = E.Range_Kutta()
    
    plt.figure(figsize=(8,5))
    
    plt.title("Trajectoire en fonction de l'angle de tir avec resistance de l'air")
    
    plt.xlabel("portee(m)")     
    plt.ylabel("hauteur(m)")
    
    plt.plot(Xc,Yc,label="45 degree")
    plt.plot(Xb,Yb,label="40 degree")
    plt.plot(Xd,Yd,label="35 degree")
    plt.plot(Xa,Ya,label="30 degree")
    plt.plot(Xe,Ye,label="25 degree")
    
    plt.legend(loc="upper right")
    plt.tight_layout()
    
    plt.savefig("Angle_parfait.png",dpi=600)
    plt.clf()
    
    theta = 45
    A = sans_air(0,0,v*np.cos(theta*(np.pi/180)), v*np.sin(theta*(np.pi/180)))
    Xa,Ya = A.portee()
    
    theta = 40
    B = sans_air(0,0,v*np.cos(theta*(np.pi/180)), v*np.sin(theta*(np.pi/180)))
    Xb,Yb = B.portee()
    
    theta = 35
    C = sans_air(0,0,v*np.cos(theta*(np.pi/180)), v*np.sin(theta*(np.pi/180)))
    Xc,Yc = C.portee()
    
    theta = 30
    D = sans_air(0,0,v*np.cos(theta*(np.pi/180)), v*np.sin(theta*(np.pi/180)))
    Xd,Yd = D.portee()
    
    theta = 25
    E = sans_air(0,0,v*np.cos(theta*(np.pi/180)), v*np.sin(theta*(np.pi/180)))
    Xe,Ye = D.portee()
    
    plt.figure(figsize=(8,5))
    
    plt.title("Trajectoire en fonction de l'angle de tir sans resistance de l'air")
    
    plt.xlabel("portee(m)")     
    plt.ylabel("hauteur(m)")
    
    plt.plot(Xa,Ya,label="45 degree")
    plt.plot(Xb,Yb,label="40 degree")
    plt.plot(Xc,Yc,label="35 degree")
    plt.plot(Xd,Yd,label="30 degree")
    plt.plot(Xe,Ye,label="25 degree")
    
    plt.legend(loc="upper right")
    plt.tight_layout()
    
    plt.savefig("Angle_parfaitsansres.png",dpi=600)
main()