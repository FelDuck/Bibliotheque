

def liredossier(name="global_temperature.csv",form=',',retform = "txt"):
    """
    lis un fichier en collone separer par le parametre form
    retourne une liste de liste qui contient les collonnes sous formes de retform
    """

    x=[]
    flag = False
    with open(name,"r") as f:

        for line in f.readlines():

            if line.startswith("#"):
                continue
            if not line.strip():
                continue

            parts = line.split(form)

            if not flag:
                flag = True
                for i in range(len(parts)):
                    x.append([])

            for i in range(len(parts)):
    
                x[i].append(parts[i])
    
        """Controls the return type"""
        
        if retform == "txt":
            return x
        elif retform == "string":
            return x
        elif retform == "float":
            for i in range(len(x)):
                for j in range(len(x[i])):
                    x[i][j] = float(x[i][j])
            return x
        elif retform == "int":
            for i in range(len(x)):
                for j in range(len(x[i])):
                    x[i][j] = int(x[i][j])
            return x
        else: return x