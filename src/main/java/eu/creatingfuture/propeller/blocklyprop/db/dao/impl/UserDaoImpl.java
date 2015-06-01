/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.creatingfuture.propeller.blocklyprop.db.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.creatingfuture.propeller.blocklyprop.db.dao.UserDao;
import eu.creatingfuture.propeller.blocklyprop.db.enums.AuthenticationProvider;
import eu.creatingfuture.propeller.blocklyprop.db.enums.Role;
import eu.creatingfuture.propeller.blocklyprop.db.generated.Tables;
import eu.creatingfuture.propeller.blocklyprop.db.generated.tables.records.SecRoleRecord;
import eu.creatingfuture.propeller.blocklyprop.db.generated.tables.records.UserRecord;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michel
 */
@Singleton
public class UserDaoImpl implements UserDao {

    private static Logger log = LoggerFactory.getLogger(UserDao.class);

    private DSLContext create;

    @Inject
    public void setDSLContext(DSLContext dsl) {
        this.create = dsl;
    }

    @Override
    public UserRecord create(String screenname, String email, String password, String salt, AuthenticationProvider authenticationProvider) {
        //create.insertInto(Tables.PROJECT).set(project).execute();
        UserRecord record = create.insertInto(Tables.USER, Tables.USER.SCREENNAME, Tables.USER.EMAIL, Tables.USER.PASSWORD, Tables.USER.SALT, Tables.USER.AUTHENTICATIONPROVIDER)
                .values(screenname, email, password, salt, authenticationProvider).returning().fetchOne();

        if (record != null && record.getId() != null && record.getId() > 0) {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.USER);
            try {
                setRoles(record.getId(), roles);
            } catch (UnauthorizedException ue) {
                // Can be dismissed because of hard coded user role
                // Print exception in case anything should change
                log.error("Creating a user should have no problem with creating its role (only USER role)", ue);
            }

        }

        return record;
    }

    @Override
    public UserRecord getUser(Long idUser) {
        return create.selectFrom(Tables.USER).where(Tables.USER.ID.equal(idUser)).fetchOne();
    }

    @Override
    public void setRoles(Long idUser, Set<Role> roles) {
        for (Role role : roles) {
            if (role != Role.USER) {
                if (!SecurityUtils.getSubject().hasRole(Role.ADMIN.name())) {
                    throw new UnauthorizedException();
                }
            }
        }

        //   System.out.println(create.select(Tables.SEC_ROLE.ID, Tables.SEC_ROLE.NAME).from(Tables.SEC_ROLE).join(Tables.SEC_USER_ROLE).on(Tables.SEC_USER_ROLE.ID_ROLE.equal(Tables.SEC_ROLE.ID)).getSQL());
        Result<SecRoleRecord> currentAssignedRoles = getRawRoles(idUser);

        for (SecRoleRecord roleRecord : currentAssignedRoles) {
            if (!roles.contains(roleRecord.getName())) {
                create.delete(Tables.SEC_USER_ROLE).where(Tables.SEC_USER_ROLE.ID_USER.equal(idUser)).and(Tables.SEC_USER_ROLE.ID_ROLE.equal(roleRecord.getId())).execute();
            }
        }
        for (Role role : roles) {
            if (!currentAssignedRoles.getValues(Tables.SEC_ROLE.NAME).contains(role)) {

                Long idRole = create.select(Tables.SEC_ROLE.ID).from(Tables.SEC_ROLE).where(Tables.SEC_ROLE.NAME.equal(role)).fetchOne(Tables.SEC_ROLE.ID);
                if (idRole == null || idRole == 0) {
                    SecRoleRecord roleRecord = createRole(role);
                    idRole = roleRecord.getId();
                }
                create.insertInto(Tables.SEC_USER_ROLE, Tables.SEC_USER_ROLE.ID_USER, Tables.SEC_USER_ROLE.ID_ROLE)
                        .values(idUser, idRole).execute();

            }
        }
    }

    private Result<SecRoleRecord> getRawRoles(Long idUser) {
        Result<SecRoleRecord> currentAssignedRoles = create.select(Tables.SEC_ROLE.ID, Tables.SEC_ROLE.NAME).from(Tables.SEC_ROLE)
                .join(Tables.SEC_USER_ROLE).on(Tables.SEC_USER_ROLE.ID_ROLE.equal(Tables.SEC_ROLE.ID))
                .where(Tables.SEC_USER_ROLE.ID_USER.equal(idUser)).fetch().into(Tables.SEC_ROLE);

        return currentAssignedRoles;
    }

    @Override
    public List<Role> getRoles(Long idUser) {
        return getRawRoles(idUser).getValues(Tables.SEC_ROLE.NAME);
    }

    private SecRoleRecord createRole(Role role) {
        SecRoleRecord record = create.insertInto(Tables.SEC_ROLE, Tables.SEC_ROLE.NAME)
                .values(role).returning().fetchOne();
        return record;
    }

}
