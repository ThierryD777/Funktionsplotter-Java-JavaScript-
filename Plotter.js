class Plotter {
    constructor(canvas, minX, maxX, minY, maxY){
        this.canvas = canvas;
        this.ctx = canvas.getContext("2d");
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
//MOn clavier est en allemand , il n y aura pas d'Accents
        this.xRange = this.maxX - this.minX;
        this.yRange = this.maxY - this.minY;
        this.xPixelsPerUnit = this.canvas.width / this.xRange;
        this.yPixelsPerUnit = this.canvas.height / this.yRange;


        //this.clearCanvas(); // Canvas löschen, bevor wir zeichnen
        this.drawAxes(this.minX, this.maxX, this.minY, this.maxY);//j'initialise mon plotter aves les limites entrees pr l'utilisateur dans le constructeur (d'ou les this.)

    }

    clearCanvas(){
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
    }

    canvasPoints(x, y){//toPixel
        const xCanvas = (x - this.minX) * this.xPixelsPerUnit;
        const yCanvas = this.canvas.height - (y - this.minY) *this.yPixelsPerUnit;
        return {x: xCanvas, y: yCanvas};
    }

    moveTo(x, y){
        const point = this.canvasPoints(x, y);
        this.x = point.x;
        this.y = point.y;
    }

    lineTo(x, y){
        const point = this.canvasPoints(x, y);
        this.ctx.beginPath();
        this.ctx.moveTo(this.x, this.y);
        this.ctx.lineTo(point.x, point.y);
        this.ctx.stroke();

        this.x = point.x;
        this.y = point.y;
    }

    drawAxes(minX,maxX,minY,maxY){//J'ai modifie ca de facon a ce que on entre maintenant nous mm les limites des axes qu'on veut dessiner , ca va aider pour le zoom
        
        this.minX = minX;//Quand je vais redessiner le tout ,evidemment je dois actualiser les donnees parceque j'ai modifie les limites et autres ...
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.xRange = this.maxX - this.minX;
        this.yRange = this.maxY - this.minY;
        this.xPixelsPerUnit = this.canvas.width / this.xRange;
        this.yPixelsPerUnit = this.canvas.height / this.yRange;
        
        
        this.ctx.fillStyle = '#f4edde';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);

        this.strokeStyle = "black";
        this.ctx.lineWidth = 2;

        for(let x = minX; x <= maxX; x++){
            const{x: pointX} = this.canvasPoints(x, 0);
            const{y: pointO} = this.canvasPoints(0,0);//ici aussi il y'A une modification , au lieu que les axes soient traces au milieu du canva , ils seront plutot traces sur la mm ligne que le point de coordonnees (0,0) comme ca si on pan l'Axe ne reste pas au milieu comme un golmon et il suit le deplecement de l'origine du repere 


            if(x !== 0){
                this.ctx.fillStyle = "black";
                this.ctx.font = "12px Arial";
                this.ctx.textAlign = "center";
                this.ctx.fillText(x, pointX, pointO + 15);
            }
        }

        for(let y = minY; y <= maxY; y++){
            const{y: pointY} = this.canvasPoints(0, y);
            const{x: pointO} = this.canvasPoints(0,0);//ici aussi il y'A une modification , au lieu que les axes soient traces au milieu du canva , ils seront plutot traces sur la mm ligne que le point de coordonnees (0,0) comme ca si on pan l'Axe ne reste pas au milieu comme un golmon et il suit le deplecement de l'origine du repere

            
            if(y !== 0){
                this.ctx.fillStyle = "black";
                this.ctx.font = "12px Arial";
                this.ctx.textAlign = "right";
                this.ctx.fillText(y, pointO - 5, pointY);
            }
        }

        this.ctx.beginPath();
        if(minX <= 0 && maxX >= 0){
            const { x } = this.canvasPoints(0, 0);
            this.ctx.moveTo(x, 0);
            this.ctx.lineTo(x, this.canvas.height);
        }
        if(minY <= 0 && maxY >= 0){
            const { y } = this.canvasPoints(0, 0);
            this.ctx.moveTo(0, y);
            this.ctx.lineTo(this.canvas.width, y);
        }
        this.ctx.stroke();
    }

    clearCanvas(){
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
    }
}