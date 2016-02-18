package waterfall_good2;

modes {low <: mid; mid <: high; };

public class A@mode<? -> X <= high> {
  attributor {
    return @mode<high>;
  }

  public void m1(String@mode<low> s) {
    s.charAt(0);  // Good!
  }

  public void m2(String@mode<mid> s) {
    s.charAt(0);  // Good!
  }

  public void m3(String@mode<high> s) {
    s.charAt(0);  // Good!
  }

}