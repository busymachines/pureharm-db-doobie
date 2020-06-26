/**
  * Copyright (c) 2017-2019 BusyMachines
  *
  * See company homepage at: https://www.busymachines.com/
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
package busymachines.pureharm.dbdoobie.test

import busymachines.pureharm.effects._
import busymachines.pureharm.db._
import busymachines.pureharm.db.testdata._
import busymachines.pureharm.dbdoobie._

/**
  *
  * Use this also as a rough outline of how you ought to structure
  * your code. Create a trait that extends the
  * [[Repo[F, MyCaseClass, MyPrimaryKeyType]]]
  * add whatever new methods/override the default ones you need here.
  *
  * Implement the queries in terms of ConnectionIO similar to
  * [[DoobiePHRTestRepo.DoobiePHRTestQueries]]
  *
  * and the final DAO in IO similar to
  * [[DoobiePHRTestRepo.DoobiePHRTestRepoImpl]]
  *
  * Voila! Bunch of free CRUD! + a lot of helpers to build
  * common queries in the [[DoobiePHRTestRepo.DoobieDoobiePHRTestTable]]
  *
  * @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 24 Sep 2019
  *
  */
private[test] trait DoobiePHRTestRepo[F[_]] extends PHRTestRepo[F]

private[test] object DoobiePHRTestRepo {

  def apply[F[_]: BracketAttempt](trans: Transactor[F]): DoobiePHRTestRepo[F] = {
    implicit val i: Transactor[F] = trans
    new DoobiePHRTestRepoImpl[F]
  }

  //----------------- implementation details -----------------
  import busymachines.pureharm.dbdoobie.implicits._
  import busymachines.pureharm.json._

  object DoobieDoobiePHRTestTable extends TableWithPK[PHRow, PhantomPK] {
    override val name: TableName = schema.PureharmRows

    val byte_col:    Column = createColumn("byte")
    val int_col:     Column = createColumn("int")
    val long_col:    Column = createColumn("long")
    val big_decimal: Column = createColumn("big_decimal")
    val string_col:  Column = createColumn("string")
    val jsonb_col:   Column = createColumn("jsonb_col")
    val opt_col:     Column = createColumn("opt_col")

    implicit private[DoobiePHRTestRepo] val pureharmJSONColMeta: Meta[PHJSONCol] =
      jsonMeta[PHJSONCol](derive.codec[PHJSONCol])

    override val showPK: Show[PhantomPK] = Show[PhantomPK]
    override val metaPK: Meta[PhantomPK] = Meta[PhantomPK]
    override val readE:  Read[PHRow]     = Read[PHRow]
    override val writeE: Write[PHRow]    = Write[PHRow]
  }

  final private object DoobiePHRTestQueries
    extends DoobieRepoQueries[PHRow, PhantomPK, DoobieDoobiePHRTestTable.type] with DoobiePHRTestRepo[ConnectionIO] {
    override def table: DoobieDoobiePHRTestTable.type = DoobieDoobiePHRTestTable
  }

  final private class DoobiePHRTestRepoImpl[F[_]: BracketAttempt](implicit
    override val transactor: Transactor[F]
  ) extends DoobieRepo[F, PHRow, PhantomPK, DoobieDoobiePHRTestTable.type] with DoobiePHRTestRepo[F] {
    override protected val queries: DoobiePHRTestQueries.type = DoobiePHRTestQueries
  }
}