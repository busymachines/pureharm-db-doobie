/*
 * Copyright 2019 BusyMachines
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package busymachines.pureharm.dbdoobie

import busymachines.pureharm.dbdoobie.internals.{SproutMetas, TransactorImplicits}

/** @author
  *   Lorand Szakacs, https://github.com/lorandszakacs
  * @since 24
  *   Sep 2019
  */
trait PureharmDBDoobieImplicitsAll
  extends doobie.syntax.AllSyntax with doobie.util.meta.SqlMeta with doobie.util.meta.TimeMeta
  with doobie.util.meta.LegacyMeta with doobie.free.Instances with doobie.postgres.Instances
  with doobie.postgres.free.Instances with doobie.postgres.syntax.ToPostgresMonadErrorOps
  with doobie.postgres.syntax.ToFragmentOps with doobie.postgres.syntax.ToPostgresExplainOps with SproutMetas
  with TransactorImplicits {}
