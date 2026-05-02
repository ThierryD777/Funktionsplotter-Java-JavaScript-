class Plotter implements Clerk{
    final String ID;
    final int height = 1300;
    final int width = 1300;
    final String libPath = "Plotter.js";
    LiveView view;
    double minX, maxX, minY, maxY;

    Plotter(LiveView view, double minX, double maxX, double minY, double maxY){
        this.view = view;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

        Clerk.load(view, libPath);
        ID = Clerk.getHashID(this);


        Clerk.write(view, "<canvas id='plotterCanvas" + ID + "' width='" + this.width + "'height='" + this.height + "' style='border:3px solid black;'></canvas>");
        Clerk.script(view, "const plotter" + ID + " = new Plotter(document.getElementById('plotterCanvas" + ID + "'), " + this.minX + ", " + this.maxX + ", " + this.minY + ", " + this.maxY + ");");

    }

    Plotter(LiveView view){
        this(view, 0, 0, 0, 0);
    }

    Plotter(double minX, double maxX, double minY, double maxY){
        this(Clerk.view(), minX, maxX, minY, maxY);
    }

    Plotter(){
        this(Clerk.view());
    }

    void moveTo(double x, double y){
        Clerk.call(view, "plotter" + ID + ".moveTo(" + x + ", " + y + ");");
    }

    void lineTo(double x, double y){
        Clerk.call(view, "plotter" + ID + ".lineTo(" + x + ", " + y + ");");
    }

    void zoom(double factor) {
        Clerk.call(view, "plotter" + ID + ".zoom(" + factor + ");");
    }
    
    void pan(double deltaX, double deltaY) {
        Clerk.call(view, "plotter" + ID + ".pan(" + deltaX + ", " + deltaY + ");");
    }

    // In der Plotter.java, rufe clearCanvas() in JavaScript über Clerk auf
    public void clearCanvas() {
        Clerk.call(view, "plotter" + ID + ".clearCanvas();");
    }
    void axes(double minX, double maxX, double minY, double maxY)//J'ajoute drawAxes a mes fonctions
    {
        Clerk.call(view, "plotter" + ID + ".drawAxes("+ minX + ", " + maxX + ", " + minY + ", " + maxY + ");");
    }

    
    
    
}
