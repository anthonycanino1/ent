// Test: Simple waterfall invariant check; class A potentially having a dynamic mode type should
// not affect the static type checking.

package waterfall_good2;

modes {low <: mid; mid <: high; };

public class A@mode<? -> (mid <= X <= high)> {
  attributor {
    return @mode<high>;
  }

  public void m1(String@mode<low> s) {
    s.charAt(0);  // Good!
  }

  public void m2(String@mode<mid> s) {
    s.charAt(0);  // Good!
  }

}
