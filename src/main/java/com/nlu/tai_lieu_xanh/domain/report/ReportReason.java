package com.nlu.tai_lieu_xanh.domain.report;

public enum ReportReason {
  SPAM,
  OFFENSIVE_CONTENT,
  HARASSMENT,
  INAPPROPRIATE_LANGUAGE,
  OTHER;

  public static String toVNReason(ReportReason reason) {
    return switch (reason) {
      case SPAM -> "Spam";
      case OFFENSIVE_CONTENT -> "Nội dung phản cảm";
      case HARASSMENT -> "Quấy rối";
      case INAPPROPRIATE_LANGUAGE -> "Ngôn ngữ không phù hợp";
      case OTHER -> "Lý do khác";
      default -> throw new IllegalArgumentException("Unknown reason: " + reason);
    };
  }
}
