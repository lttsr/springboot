package sample.model.master;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sample.context.DomainEntity;
import sample.context.orm.OrmRepository;
import sample.model.master.type.AuthorityType;
import sample.model.master.type.StatusType;

@Entity
@Data
@Table(name = "company_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements DomainEntity {
    /** 利用者ID */
    @Id
    private String userId;
    /** 利用者グループID */
    private String groupId;
    /** 氏名 */
    @NotNull
    private String name;
    /** 氏名カナ */
    @NotNull
    private String nameKana;
    /** メールアドレス */
    private String mailAddress;
    /** プロフィール */
    private String profile;
    /** 備考 */
    private String description;
    /** 利用権限 */
    @NotNull
    @Enumerated
    private AuthorityType authorityType;
    /** 利用者状態 */
    @NotNull
    @Enumerated
    private StatusType statusType;

    /** 利用者を登録します。 */
    public static User register(OrmRepository rep, RegisterUser param) {
        return rep.save(param.create());
    }

    @Builder
    public static record RegisterUser(
            @NotNull String userId,
            String groupId,
            @NotNull String name,
            @NotNull String nameKana,
            String mailAddress,
            String profile,
            String description) {

        public User create() {
            return User.builder()
                    .userId(this.userId)
                    .groupId(this.groupId)
                    .name(this.name)
                    .nameKana(this.nameKana)
                    .mailAddress(this.mailAddress)
                    .profile(this.profile)
                    .description(this.description)
                    .authorityType(AuthorityType.USER)
                    .statusType(StatusType.Temporary)
                    .build();
        }
    }

}
