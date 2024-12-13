package mkqq.BLL;

import java.util.List;

import mkqq.DAO.MemberDAO;
import mkqq.DTO.MemberDTO;

public class MemberBLL {

    private MemberDAO memberDAO;

    public MemberBLL() {
        memberDAO = new MemberDAO();
    }

    public boolean createMember(MemberDTO member) {
        return memberDAO.createMember(member);
    }

    public boolean insertMember(MemberDTO member) {
        return memberDAO.insertMember(member);
    }

    public boolean updateMember(MemberDTO member) {
        return memberDAO.updateMember(member);
    }

    public boolean deleteMember(String member) {
        return memberDAO.deleteMember(member);
    }

    public String getNewId() {
        return memberDAO.getNewId();
    }

    public MemberDTO getMemberfromID(String id) {
        return memberDAO.getMemberfromID(id);
    }
    public List<MemberDTO> getMemberDTOS(){
        return memberDAO.getMemberDTOS();
    }
}
