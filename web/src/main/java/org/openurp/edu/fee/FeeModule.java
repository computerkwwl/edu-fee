/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.fee;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.openurp.edu.fee.service.FeeDetailImportListener;
import org.openurp.edu.fee.service.TuitionFeeImportListener;
import org.openurp.edu.fee.service.impl.FeeDefaultServiceImpl;
import org.openurp.edu.fee.service.impl.FeeDetailServiceImpl;
import org.openurp.edu.fee.service.impl.TuitionFeeServiceImpl;
import org.openurp.edu.fee.web.action.CourseAndFeeAction;
import org.openurp.edu.fee.web.action.CreditFeeDefaultAction;
import org.openurp.edu.fee.web.action.FeeDefaultAction;
import org.openurp.edu.fee.web.action.FeeDefaultConfigAction;
import org.openurp.edu.fee.web.action.FeeDetailAction;
import org.openurp.edu.fee.web.action.FeeOfStdAction;
import org.openurp.edu.fee.web.action.FeeSearchAction;
import org.openurp.edu.fee.web.action.FeeStatAction;
import org.openurp.edu.fee.web.action.SquadAndFeeAction;
import org.openurp.edu.fee.web.action.TuitionFeeAction;

/**
 * @author zhouqi 2018年9月27日
 */
public class FeeModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind("feeDefaultService", FeeDefaultServiceImpl.class);
    bind("tuitionFeeService", TuitionFeeServiceImpl.class);
    bind("feeDetailService", FeeDetailServiceImpl.class);

    bind(SquadAndFeeAction.class, CourseAndFeeAction.class, CreditFeeDefaultAction.class,
        FeeDefaultAction.class, FeeDefaultConfigAction.class, FeeSearchAction.class, FeeOfStdAction.class,
        FeeStatAction.class);
    bind(FeeDetailAction.class).property("importerListeners", list(FeeDetailImportListener.class));
    bind(TuitionFeeAction.class).property("importerListeners", list(TuitionFeeImportListener.class));
  }
}
