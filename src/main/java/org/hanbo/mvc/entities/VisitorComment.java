package org.hanbo.mvc.entities;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "visitorcomment")
@Indexed
public class VisitorComment
{
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;
   
   @Column(name = "commenter", nullable = false, length = 96)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String commenter;
   
   @Column(name = "commenteremail", nullable = false, length = 96)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String commenterEmail;
   
   @Column(name = "sourceip", nullable = false, length = 39)
   private String sourceIp;
   
   @Column(name = "title", nullable = true, length = 128)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String title;
   
   @Column(name = "content", nullable = false, length = 512)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String content;
   
   @Column(name = "isprivate", nullable = false)
   private boolean commentPrivate;
   
   @Column(name = "isapproved", nullable = false)
   private boolean commentApproved;

   @Column(name = "createdate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;
   
   @Column(name = "updatedate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDate;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ownerid")
   private LoginUser owner;
  
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "articleid")
   private Article relatedArticle;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "imageid")
   private Image relatedImage;
   
   @OneToMany(fetch = FetchType.LAZY, mappedBy="parentComment")
   private Set<VisitorComment> childComments;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "parentcommentid")
   private VisitorComment parentComment;
   
   public VisitorComment()
   {
      childComments = new HashSet<VisitorComment>();
   }

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getCommenter()
   {
      return commenter;
   }

   public void setCommenter(String commenter)
   {
      this.commenter = commenter;
   }

   public String getCommenterEmail() {
      return commenterEmail;
   }

   public void setCommenterEmail(String commenterEmail) {
      this.commenterEmail = commenterEmail;
   }

   public String getSourceIp() {
      return sourceIp;
   }

   public void setSourceIp(String sourceIp) {
      this.sourceIp = sourceIp;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public boolean isCommentPrivate() {
      return commentPrivate;
   }

   public void setCommentPrivate(boolean commentPrivate) {
      this.commentPrivate = commentPrivate;
   }

   public boolean isCommentApproved() {
      return commentApproved;
   }

   public void setCommentApproved(boolean commentApproved) {
      this.commentApproved = commentApproved;
   }

   public Date getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }

   public Date getUpdateDate() {
      return updateDate;
   }

   public void setUpdateDate(Date updateDate) {
      this.updateDate = updateDate;
   }

   public LoginUser getOwner() {
      return owner;
   }

   public void setOwner(LoginUser owner) {
      this.owner = owner;
   }

   public Article getRelatedArticle() {
      return relatedArticle;
   }

   public void setRelatedArticle(Article relatedArticle) {
      this.relatedArticle = relatedArticle;
   }

   public Image getRelatedImage() {
      return relatedImage;
   }

   public void setRelatedImage(Image relatedImage) {
      this.relatedImage = relatedImage;
   }

   public Set<VisitorComment> getChildComments() {
      return childComments;
   }

   public void setChildComments(Set<VisitorComment> childComments) {
      this.childComments = childComments;
   }

   public VisitorComment getParentComment() {
      return parentComment;
   }

   public void setParentComment(VisitorComment parentComment) {
      this.parentComment = parentComment;
   }
}
