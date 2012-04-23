package assignment10;

import java.lang.Math;

public class Complex {
  
  protected double re;
  protected double im;
  
  public Complex(double real, double imag) {
    re = real;
    im = imag;
  }
  
  public double getReal(){
    return re;
  }
  
  public double getImaginary(){
    return im;
  }

  @Override
  public String toString(){
    return re + ((Math.signum(im) >= 0)?" + ":" - ") + Math.abs(im)+"i";
  }
  
  @Override
  public boolean equals(Object obj){
    if(this == obj)
      return true;
    if(obj == null)
      return false;
    if(getClass() != obj.getClass())
      return false;
    Complex c = (Complex)obj;
    return (c.re == this.re) && (c.im == this.im);
  }
  

}
