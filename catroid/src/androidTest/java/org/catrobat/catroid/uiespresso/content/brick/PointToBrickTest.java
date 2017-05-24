/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2017 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.content.brick;

import android.support.test.InstrumentationRegistry;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.bricks.FlashBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PointToBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.io.StorageHandler;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.uiespresso.util.BaseActivityInstrumentationRule;
import org.catrobat.catroid.uiespresso.util.UiTestUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import javax.annotation.Nonnull;

import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.checkIfBrickAtPositionShowsString;
import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.checkIfSpinnerOnBrickAtPositionShowsString;
import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.clickSelectCheckSpinnerValueOnBrick;

public class PointToBrickTest {
	private int brickPosition;

	PointToBrick pointToBrick;
	Project project;


	// MainMenuActivity
	@Rule
	public BaseActivityInstrumentationRule<ScriptActivity> baseActivityTestRule = new
			BaseActivityInstrumentationRule<>(ScriptActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		//BrickTestUtils.createProjectAndGetStartScript("pointToBrickTests1").addBrick(new PointToBrick());
		createProject("lookAtBrickTest");
		baseActivityTestRule.launchActivity(null);
		//createProject();
		//brickPosition = 1;
		//baseActivityTestRule.launchActivity(null);
	}

	@Test
	public void testPointToBrick() {

		//createProject("lookAtBrickTest");



		checkIfBrickAtPositionShowsString(0, R.string.brick_when_started);
		//checkIfBrickAtPositionShowsString(brickPosition, R.string.brick_point_to);



	}



	private void createProject(String projectName) {
		Project project = new Project(null, projectName);

		Sprite baseSprite = new SingleSprite("baseSprite");
		Sprite lookAtSprite = new SingleSprite("lookAtSprite");

		//Script baseSpriteScript = new StartScript();
		Script lookAtSpriteScript = new StartScript();



		//baseSprite.addScript(baseSpriteScript);

		LookData baseSpriteLookData = new LookData();
		String baseLookDataImageName = "red_image.bmp";

		baseSpriteLookData.setLookFilename(baseLookDataImageName);
		baseSprite.getLookDataList().add(baseSpriteLookData);


		lookAtSprite.addScript(lookAtSpriteScript);

		LookData lookAtSpriteLookData = new LookData();
		String lookAtLookDataImageName = "blue_image.bmp";

		lookAtSpriteLookData.setLookFilename(lookAtLookDataImageName);
		lookAtSprite.getLookDataList().add(lookAtSpriteLookData);




		lookAtSpriteScript.addBrick(new PlaceAtBrick(300, 400));
		lookAtSpriteScript.addBrick(new PointToBrick());
		lookAtSpriteScript.addBrick(new SetVariableBrick());
		//lookAtSpriteScript.addBrick(new Show);
		lookAtSprite.addScript(lookAtSpriteScript);


		StorageHandler.getInstance().saveProject(project);
		project.getDefaultScene().addSprite(lookAtSprite);
		project.getDefaultScene().addSprite(baseSprite);
		File baseImageFile = UiTestUtils.saveFileToProject(project.getName(), project.getDefaultScene().getName(),
				baseLookDataImageName, org.catrobat.catroid.test.R.raw.red_image,
				InstrumentationRegistry.getContext(),
				UiTestUtils.FileTypes.IMAGE);

		File lookAtImageFile = UiTestUtils.saveFileToProject(project.getName(), project.getDefaultScene().getName(),
				lookAtLookDataImageName, org.catrobat.catroid.test.R.raw.blue_image,
				InstrumentationRegistry.getContext(),
				UiTestUtils.FileTypes.IMAGE);
		lookAtSpriteLookData.setLookFilename(lookAtImageFile.getName());
		baseSpriteLookData.setLookFilename(baseImageFile.getName());


		StorageHandler.getInstance().saveProject(project);
		ProjectManager.getInstance().setProject(project);
		ProjectManager.getInstance().setCurrentSprite(lookAtSprite);

	}
}
