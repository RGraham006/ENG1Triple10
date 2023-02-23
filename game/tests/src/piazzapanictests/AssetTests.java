package piazzapanictests;

import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests that the assets are present.
 */
@RunWith(GdxTestRunner.class)
public class AssetTests {
  /**
   * Tests that the chef assets are present.
   */
  @Test
  public void testChefAssetExists() {
    assertTrue("This test will only pass when the chef1.png asset exists.",
        Gdx.files.internal("Chefs/Chef1/chef1.png").exists());
  }
}